package com.uxstate.skycast.presentation.forecast.tabs

import androidx.compose.runtime.Composable
import com.uxstate.skycast.presentation.forecast.ForecastState
import com.uxstate.skycast.utils.shortDate
import com.uxstate.skycast.utils.shortDayOfWeek

sealed class TabItem(
    val dayOfWeek: String,
    val dayOfTheMonth: String,
    val content: @Composable () -> Unit
) {

    data class DayOne(val state: ForecastState, val onRefreshForecast: () -> Unit, val page: Int) :
        TabItem(
                dayOfWeek = shortDayOfWeek(page.toLong()),
                dayOfTheMonth = shortDate(page.toLong()),
                content = {
                    WeatherForecast(
                            state = state,
                            page = page,
                            onRefreshForecast = onRefreshForecast
                    )
                })



}