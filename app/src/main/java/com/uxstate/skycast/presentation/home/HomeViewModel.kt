package com.uxstate.skycast.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxstate.skycast.domain.location.LocationTracker
import com.uxstate.skycast.domain.prefs.DataStoreOperations
import com.uxstate.skycast.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.http.GET
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
        getWeatherInfo()
    }


    fun getLastLocation(){
        viewModelScope.launch {

            tracker.getCurrentLocation().data?.let {

                geoPoint ->

            } ?: run {
                _uiState.update { it.copy(errorMessage = "Error getting location") }

            }



            }


    }
    fun getWeatherInfo(){

        Timber.i("GetWeather called")
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {

            tracker.getCurrentLocation().data?.let {

                geoPoint ->

                repository.getCurrentWeather(geoPoint).collect{

                    response ->

                    prefs.appPreferences.collect{
                        appPreferences ->

                        response.data?.cityId?.let {
                            saveCityId(it)
                        }

                        _uiState.update { it.copy(currentWeather = response.data,appPreferences = appPreferences) }
                    }
                }
            }
        }

        _uiState.update {
            it.copy(isLoading = false)
        }
    }

    private fun saveCityId(cityId:Int){

        viewModelScope.launch {
            prefs.updateCityId(cityId = cityId)
        }
    }

    fun refreshWeather() {

        getWeatherInfo()
    }
}