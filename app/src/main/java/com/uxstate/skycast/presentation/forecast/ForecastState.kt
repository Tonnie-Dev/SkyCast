package com.uxstate.skycast.presentation.forecast

import com.uxstate.skycast.domain.model.ForecastWeather
import com.uxstate.skycast.domain.prefs.AppPreferences
import com.uxstate.skycast.domain.prefs.TempUnit
import com.uxstate.skycast.domain.prefs.Theme

data class ForecastState(
    val forecastData: List<ForecastWeather> = emptyList(),
    val filteredForecast: List<ForecastWeather> = emptyList(),
    val isLoading: Boolean = false,
) {

    companion object {

        val appPreferencesInitialState =
            AppPreferences(
                    theme = Theme.SYSTEM,
                    tempUnit = TempUnit.CELSIUS,
                    savedCityId = -1
            )
    }
}