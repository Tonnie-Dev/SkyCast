package com.uxstate.skycast.presentation.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxstate.skycast.domain.location.LocationTracker
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
import javax.inject.Inject


@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val prefs: DataStoreOperations
) : ViewModel() {


    private val _state = MutableStateFlow(ForecastState())
    val state = _state.asStateFlow()

    init {

        getCityId()

    }

    private fun getForecastWeather(cityId:Int? = _state.value.cityId) {

        cityId?.let { id ->

            repository.getForecastWeather(id)
                    .onEach { result ->

                        when (result) {

                            is Resource.Loading -> {

                                _state.update { it.copy(isLoading = result.isLoading) }
                            }

                            is Resource.Error -> {


                                _state.update { it.copy(errorMessage = result.errorMessage) }
                            }

                            is Resource.Success -> {

                                result.data?.let { forecastWeather ->

                                    _state.update { it.copy(forecastData = forecastWeather) }
                                }
                            }

                        }

                    }
                    .launchIn(viewModelScope)
        }


    }

    private fun getCityId() {

        viewModelScope.launch {
            prefs.appPreferences.collectLatest { savedPrefs ->

                _state.update { it.copy(cityId = savedPrefs.savedCityId) }
                getForecastWeather( savedPrefs.savedCityId)
            }

        }

    }


    fun refreshForecastWeather(){

        getForecastWeather()
    }
}

