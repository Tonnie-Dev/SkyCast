package com.uxstate.skycast.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxstate.skycast.domain.location.LocationTracker
import com.uxstate.skycast.domain.prefs.DataStoreOperations
import com.uxstate.skycast.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val tracker: LocationTracker,
    val prefs: DataStoreOperations
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    fun getWeatherInfo(){

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
            it.copy(isLoading = true)
        }
    }

    private fun saveCityId(cityId:Int){

        viewModelScope.launch {
            prefs.updateCityId(cityId = cityId)
        }
    }
}