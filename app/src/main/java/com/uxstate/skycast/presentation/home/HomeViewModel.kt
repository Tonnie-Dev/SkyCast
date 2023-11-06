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

        Timber.i("Inside inti")
        observePrefsFlow()


        viewModelScope.launch {

            connectivityObserver.observe()
                    .collectLatest {  status ->
                        Timber.i("Status B is ${_state.value.netWorkStatus}")
                        _state.update { it.copy(netWorkStatus = status) }

                        Timber.i("Status A is ${_state.value.netWorkStatus}")

                    }
        }

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

    private fun getCurrentLocation(fetchFromRemote: Boolean = false) {

        viewModelScope.launch {

            when (val result = tracker.getCurrentLocation()) {

                is Resource.Success -> {
                    result.data?.let {

                        geoPoint ->

                        _state.update {
                            it.copy(
                                    geoPoint = GeoPoint(
                                            latitude = geoPoint.latitude,
                                            geoPoint.longitude
                                    ),
                                    isLocationNull = false,
                            )
                        }
                        if (_state.value.netWorkStatus == ConnectivityObserver.Status.UNAVAILABLE && fetchFromRemote) {
                            getCurrentWeather(geoPoint, false)
                            _state.update { it.copy(isShowSnackBar = true) }
                        } else {

                            getCurrentWeather(geoPoint, fetchFromRemote)
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

                    _state.update { it.copy(errorMessage = result.errorMessage) }
                }

                else -> Unit
            }

        }

    }

    private fun getCurrentWeather(geoPoint: GeoPoint, fetchFromRemote: Boolean) {

        repository.getCurrentWeather(geoPoint, fetchFromRemote)
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


    /*  fun refreshWeather() {
          getCurrentLocation()

      }*/

    fun onEvent(event: HomeEvent) {

        when (event) {

            is HomeEvent.OnRefresh -> {

                getCurrentLocation(fetchFromRemote = true)

            }

            is HomeEvent.OnContinue -> {

                resetLocationInfo()

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

    private fun resetLocationInfo() {
        viewModelScope.launch {

            _state.update { it.copy(isLocationNull = false, isShowDialog = true) }
            isLocationEnabled.value = locationManager.isLocationEnabled
        }

    }

}