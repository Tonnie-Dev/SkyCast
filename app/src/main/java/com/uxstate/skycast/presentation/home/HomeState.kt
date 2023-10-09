package com.uxstate.skycast.presentation.home

import com.uxstate.skycast.domain.model.CurrentWeather
import com.uxstate.skycast.domain.prefs.AppPreferences
import com.uxstate.skycast.domain.prefs.TempUnit
import com.uxstate.skycast.domain.prefs.Theme

data class HomeState(
    val currentWeather: CurrentWeather? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val appPreferences: AppPreferences = appPreferencesInitialState
) {


    //serves to initialize app preferences initial state
    companion object {
        val appPreferencesInitialState =
            AppPreferences(
                    theme = Theme.SYSTEM,
                    tempUnit = TempUnit.CELSIUS,
                    savedCityId = -1
            )
    }
}