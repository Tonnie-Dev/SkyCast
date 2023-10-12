package com.uxstate.skycast.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxstate.skycast.domain.location.LocationTracker
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.domain.prefs.DataStoreOperations
import com.uxstate.skycast.domain.repository.WeatherRepository
import com.uxstate.skycast.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val prefs: DataStoreOperations
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    init {
Timber.i("Init block called")
        getLastLocation()

    }

    private fun getLastLocation() {

        Timber.i("Get Location called")
        viewModelScope.launch {

            tracker.getCurrentLocation().data?.let {

                geoPoint ->
                Timber.i("Geopoint is $geoPoint")
                _uiState.update {
                    it.copy(
                            geoPoint = GeoPoint(
                                    latitude = geoPoint.latitude,
                                    geoPoint.longitude
                            )
                    )
                }
                getCurrentWeather(geoPoint)
            } ?: run {

                Timber.i("Error in getting location")
                _uiState.update { it.copy(errorMessage = "Error getting location") }

            }


        }


    }

    private fun getCurrentWeather(geoPoint: GeoPoint = _uiState.value.geoPoint) {

        Timber.i("GetCurrentWeather() called - $geoPoint")
        repository.getCurrentWeather(geoPoint)
                .onEach {


                    result ->

                    when (result) {

                        is Resource.Error -> {
                            Timber.i("Repo Error - ${result.errorMessage} ")
                            _uiState.update { it.copy(errorMessage = result.errorMessage) }
                        }

                        is Resource.Loading -> {

                            Timber.i("Loading called")
                            _uiState.update { it.copy(isLoading = result.isLoading) }

                        }

                        is Resource.Success -> {

Timber.i("Success in the Repo - ${result.data}")
                            result.data?.let { currentWeather ->
                                Timber.i("Repo Success - $currentWeather ")
                                saveCityId(currentWeather.cityId)
                             _uiState.update { it.copy(currentWeather = currentWeather) }
                            }
                        }
                    }

                }
                .launchIn(viewModelScope)

    }

    fun getWeatherInfo() {

        Timber.i("GetWeather called")
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {

            tracker.getCurrentLocation().data?.let {

                geoPoint ->

                repository.getCurrentWeather(geoPoint)
                        .collect {

                            response ->

                            prefs.appPreferences.collect { appPreferences ->

                                response.data?.cityId?.let {
                                    saveCityId(it)
                                }

                                _uiState.update {
                                    it.copy(
                                            currentWeather = response.data,
                                            appPreferences = appPreferences
                                    )
                                }
                            }
                        }
            }
        }

        _uiState.update {
            it.copy(isLoading = false)
        }
    }

    private fun saveCityId(cityId: Int) {

        viewModelScope.launch {
            prefs.updateCityId(cityId = cityId)
        }
    }

    fun refreshWeather() {
        getCurrentWeather()
        //  getWeatherInfo()
    }
}