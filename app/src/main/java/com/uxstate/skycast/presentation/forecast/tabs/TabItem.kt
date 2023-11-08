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

    data class DayOne(val state: ForecastState, val onRefreshForecast: () -> Unit) :
        TabItem(
                dayOfWeek = LocalDate.now()
                        .shortDayOfWeek(0),
                dayOfTheMonth = LocalDate.now()
                        .shortDate(0),
                content = {

                    WeatherForecast(
                            state = state,
                            onRefreshForecast = onRefreshForecast
                    )
                })

    data class DayTwo(val state: ForecastState, val onRefreshForecast: () -> Unit) :
        TabItem(
                dayOfWeek = LocalDate.now()
                        .shortDayOfWeek(1),
                dayOfTheMonth = LocalDate.now()
                        .shortDate(1),
                content = {
                    WeatherForecast(
                            state = state,
                            onRefreshForecast = onRefreshForecast
                    )

                })

    data class DayThree(
        val state: ForecastState,
        val onRefreshForecast: () -> Unit
    ) : TabItem(
            dayOfWeek = LocalDate.now()
                    .shortDayOfWeek(2),
            dayOfTheMonth = LocalDate.now()
                    .shortDate(2),
            content = {

                WeatherForecast(
                        state = state,
                        onRefreshForecast = onRefreshForecast
                )
            })


    data class DayFour(
        val state: ForecastState,
        val onRefreshForecast: () -> Unit
    ) : TabItem(
            dayOfWeek = LocalDate.now()
                    .shortDayOfWeek(3),
            dayOfTheMonth = LocalDate.now()
                    .shortDate(3),
            content = {
                WeatherForecast(
                        state = state,
                        onRefreshForecast = onRefreshForecast
                )
            })

    data class DayFive(
        val state: ForecastState,
        val onRefreshForecast: () -> Unit
    ) : TabItem(
            dayOfWeek = LocalDate.now()
                    .shortDayOfWeek(4),
            dayOfTheMonth = LocalDate.now()
                    .shortDate(4),
            content = {
                WeatherForecast(
                        state = state,
                        onRefreshForecast = onRefreshForecast
                )
            })

}