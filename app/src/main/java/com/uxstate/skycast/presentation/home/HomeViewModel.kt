package com.uxstate.skycast.presentation.home

import android.app.Activity
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxstate.skycast.domain.connectivity.ConnectivityObserver
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

@RequiresApi(Build.VERSION_CODES.P)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationManager: LocationManager,
    private val repository: WeatherRepository,
    private val tracker: LocationTracker,
    private val prefs: DataStoreOperations,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    var isLocationEnabled = mutableStateOf(locationManager.isLocationEnabled)
        private set

    init {

        observePrefsFlow()
        observeNetworkStatus()
        getCurrentLocation()

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

    private fun observeNetworkStatus() {

        connectivityObserver.observe()
                .onEach { status ->
                    _state.update { it.copy(netWorkStatus = status) }

                }
                .launchIn(viewModelScope)
    }

    private fun getCurrentLocation() {

        viewModelScope.launch {

            when (val result = tracker.getCurrentLocation()) {

                is Resource.Success -> {
                    result.data?.let {

                        geoPoint ->
                        Timber.i("LatLng is ${geoPoint.latitude}, ${geoPoint.longitude}")
                        _state.update {


                            it.copy(
                                    geoPoint = GeoPoint(
                                            latitude = geoPoint.latitude,
                                            geoPoint.longitude
                                    ),
                                    isLocationNull = false,
                            )
                        }

                        if (connectivityObserver.isInternetConnectionAvailable(_state.value.netWorkStatus)) {



                            getCurrentWeather(geoPoint)

                        } else {
                            _state.update {
                                it.copy(

                                        isShowNoConnectionWidget = true
                                )
                            }

                        }


                    } ?: run {

                        _state.update {
                            it.copy(
                                    isLocationNull = true,
                                    isShowDialog = true
                            )
                        }

                    }

                }

                is Resource.Error -> {

                    _state.update {
                        it.copy(
                                errorMessage = result.errorMessage
                        )
                    }
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


    fun onEvent(event: HomeEvent) {

        when (event) {

            is HomeEvent.OnRefresh -> {
                resetHomeState()
                getCurrentLocation()

            }

            is HomeEvent.OnContinue -> {

                resetHomeState()

                if (event.isPermissionGranted) {

                    getCurrentLocation()
                }
                _state.update { it.copy(isShowDialog = !event.isPermissionGranted) }
                observePrefsFlow()

            }

            is HomeEvent.OnExit -> {
                val activity = event.context as Activity
                ActivityCompat.finishAffinity(activity)
            }

            else -> {

                _state.update { it.copy(isShowDialog = false) }
            }

        }

    }

    private fun resetHomeState() {
        viewModelScope.launch {

            _state.update {
                it.copy(
                        isLocationNull = false,
                        isShowDialog = true,
                        isShowNoConnectionWidget = false
                )
            }
            isLocationEnabled.value = locationManager.isLocationEnabled
        }

    }

}