package com.uxstate.skycast.presentation.forecast.tabs

import androidx.compose.runtime.Composable
import com.uxstate.skycast.presentation.forecast.ForecastState
import com.uxstate.skycast.utils.shortDate
import com.uxstate.skycast.utils.shortDayOfWeek
import java.time.LocalDate

sealed class TabItem(
    val dayOfWeek: String,
    val dayOfTheMonth: String,
    val content: @Composable () -> Unit
) {

    data class DayOne(val state: ForecastState, val onRefreshForecast: () -> Unit, val page: Int) :
        TabItem(
                dayOfWeek = LocalDate.now()
                        .shortDayOfWeek(0),
                dayOfTheMonth = LocalDate.now()
                        .shortDate(0),
                content = {
                    WeatherForecast(
                            state = state,
                            page = page,
                            onRefreshForecast = onRefreshForecast
                    )
                })



}