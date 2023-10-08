package com.uxstate.skycast.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxstate.skycast.domain.location.LocationTracker
import com.uxstate.skycast.domain.prefs.DataStoreOperations
import com.uxstate.skycast.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val tracker: LocationTracker,
    val prefs: DataStoreOperations
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState)
    val uiState = _uiState.asStateFlow()

    private fun saveCityId(cityId:Int){

        viewModelScope.launch {
            prefs.updateCityId(cityId = cityId)
        }
    }
}