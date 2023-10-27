package com.uxstate.skycast.presentation.home

import android.location.LocationManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxstate.skycast.domain.location.LocationTracker
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.domain.prefs.AppPreferences
import com.uxstate.skycast.domain.prefs.DataStoreOperations
import com.uxstate.skycast.domain.repository.WeatherRepository
import com.uxstate.skycast.presentation.main.MainActivity
import com.uxstate.skycast.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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


    init {
        observePrefsFlow()

        updateLocationServiceStatus()
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


            when (val locData = tracker.getCurrentLocation()) {

                is Resource.Success -> {
                    tracker.getCurrentLocation().data?.let {

                        geoPoint ->

                        _state.update {
                            it.copy(
                                    geoPoint = GeoPoint(
                                            latitude = geoPoint.latitude,
                                            geoPoint.longitude
                                    )
                            )
                        }
                        getCurrentWeather(geoPoint)
                    } ?: run {


                        _state.update {
                            it.copy(
                                    errorMessage = "Error getting location"
                            )
                        }

                    }

                }

                is Resource.Error -> {


                }

                else -> Unit
            }


        }


    }

    private fun getCurrentWeather(geoPoint: GeoPoint = _state.value.geoPoint) {


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
        getCurrentWeather()

    }

    fun onEvent(event: HomeEvent){


        when(event){

            is HomeEvent.GpsEnabledEvent -> {
                _state.update {
                    it.copy(
                            isLocationEnabled = true
                    )
                }

            }
            is HomeEvent.GpsDisabledEvent -> {

                _state.update {
                    it.copy(
                            isLocationEnabled = false
                    )
                }
            }
        }
    }

    private fun updateLocationServiceStatus() {


        val gpsProviderString = LocationManager.GPS_PROVIDER
        _state.update {
            it.copy(
                    isLocationEnabled = locationManager.isProviderEnabled(
                            gpsProviderString
                    )
            )
        }


    }


    fun updateCurrentLocationData() {
        getLastLocation()
    }
}