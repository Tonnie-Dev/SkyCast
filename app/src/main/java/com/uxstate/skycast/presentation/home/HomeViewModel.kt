package com.uxstate.skycast.presentation.home

import androidx.lifecycle.ViewModel
import com.uxstate.skycast.domain.location.LocationTracker
import com.uxstate.skycast.domain.prefs.DataStoreOperations
import com.uxstate.skycast.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val tracker: LocationTracker,
    val prefs: DataStoreOperations
) : ViewModel() {


}