package com.uxstate.skycast.presentation.forecast

import com.uxstate.skycast.domain.model.ForecastWeather
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.domain.prefs.AppPreferences
import com.uxstate.skycast.domain.prefs.TempUnit
import com.uxstate.skycast.domain.prefs.Theme

data class ForecastState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val cityId:Int? = null,
    val forecastData: List<ForecastWeather> = emptyList(),
    val filteredForecast: List<ForecastWeather> = emptyList(),
    val prefs: AppPreferences = appPreferencesInitialState


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