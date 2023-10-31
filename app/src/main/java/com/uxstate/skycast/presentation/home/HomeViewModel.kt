package com.uxstate.skycast.presentation.home

import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxstate.skycast.domain.location.LocationTracker
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.domain.prefs.AppPreferences
import com.uxstate.skycast.domain.prefs.DataStoreOperations
import com.uxstate.skycast.domain.repository.WeatherRepository

import com.uxstate.skycast.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val tracker: LocationTracker,
    private val locationManager: LocationManager,
    private val prefs: DataStoreOperations
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.P)
    val isLocationEnabled = mutableStateOf(locationManager.isLocationEnabled)





    init {
        observePrefsFlow()
        getLastLocation()
    }

    private fun observePrefsFlow() {

        viewModelScope.launch {

            prefs.appPreferences.collectLatest {

                appPrefs ->

                _state.update {
                    it.copy(
                            appPreferences = AppPreferences(
                                    tempUnit = appPrefs.tempUnit,
                                    theme = appPrefs.theme,
                                    savedCityId = appPrefs.savedCityId
                            )
                    )
                }
            }
        }
    }

    private fun getLastLocation() {

        viewModelScope.launch {

            when (val result = tracker.getCurrentLocation()) {

                is Resource.Success -> {
                    result.data?.let {

                        geoPoint ->
                        Timber.i("Success - Data is NOT null")
                        _state.update {
                            it.copy(
                                    geoPoint = GeoPoint(
                                            latitude = geoPoint.latitude,
                                            geoPoint.longitude
                                    ), isLocationNull = false, isShowDialog = false
                            )
                        }

                        getCurrentWeather(geoPoint)

                    } ?: run {

                        Timber.i("Success - Data IS NULL")
                        _state.update {
                            it.copy(
                                    isLocationNull = true, isShowDialog = true
                            )
                        }

                    }

                }

                is Resource.Error -> {

                    _state.update { it.copy(errorMessage = result.errorMessage) }
                }

                else -> Unit
            }

        }

    }

    private fun getCurrentWeather(geoPoint: GeoPoint) {

        repository.getCurrentWeather(geoPoint)
                .onEach {


                    result ->

                    when (result) {

                        is Resource.Error -> {

                            _state.update { it.copy(errorMessage = result.errorMessage) }
                        }

                        is Resource.Loading -> {


                            _state.update { it.copy(isLoading = result.isLoading) }

                        }

                        is Resource.Success -> {


                            result.data?.let { currentWeather ->

                                saveCityId(currentWeather.cityId)
                                _state.update { it.copy(currentWeather = currentWeather) }
                            }
                        }
                    }

                }
                .launchIn(viewModelScope)

    }


    private fun saveCityId(cityId: Int) {

        viewModelScope.launch {
            prefs.updateCityId(cityId = cityId)
        }
    }


    fun refreshWeather() {
        getLastLocation()
       /* _state.value.geoPoint?.let {
            getCurrentWeather(it)

        }?: kotlin.run { _state.update { it.copy(isLocationNull = true) } }*/


    }

    fun onEvent(event: HomeEvent) {


        when (event) {


            is HomeEvent.OnRetry -> {

                getLastLocation()
            }
            else -> Unit
        }

    }


}