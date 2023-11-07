package com.uxstate.skycast.presentation.forecast.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.uxstate.skycast.presentation.forecast.ForecastState
import java.time.LocalDate

sealed class TabItem(
    val dayOfWeek: String,
    val dayOfTheMonth: String,
    val content:@Composable () -> Unit
) {


    data class DayOne(val state: ForecastState, val onRefreshForecast: () -> Unit) :
        TabItem(
                dayOfWeek = LocalDate.now().dayOfWeek.name.substring(0..2),
                dayOfTheMonth = LocalDate.now().dayOfMonth.toString() + " " +
                        LocalDate.now().month.name.substring(0..2),
                content = {

                WeatherForecast(
                            state = state,
                            onRefreshForecast = onRefreshForecast
                    )
                })

    data class DayTwo(val state: ForecastState, val onRefreshForecast: () -> Unit) :
        TabItem(
                dayOfWeek = LocalDate.now()
                        .plusDays(1).dayOfWeek.name.substring(0..2),
                dayOfTheMonth = LocalDate.now()
                        .plusDays(1).dayOfMonth.toString() + " " +
                        LocalDate.now()
                                .plusDays(1).month.name.substring(0..2),
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
                    .plusDays(2).dayOfWeek.name.substring(0..2),
            dayOfTheMonth = LocalDate.now()
                    .plusDays(2).dayOfMonth.toString() + " " +
                    LocalDate.now()
                            .plusDays(2).month.name.substring(0..2),
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
                    .plusDays(3).dayOfWeek.name.substring(0..2),
            dayOfTheMonth = LocalDate.now()
                    .plusDays(3).dayOfMonth.toString() + " " +
                    LocalDate.now()
                            .plusDays(3).month.name.substring(0..2),
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
                    .plusDays(4).dayOfWeek.name.substring(0..2),
            dayOfTheMonth = LocalDate.now()
                    .plusDays(4).dayOfMonth.toString() + " " +
                    LocalDate.now()
                            .plusDays(4).month.name.substring(0..2),
            content = {



                WeatherForecast(
                        state = state,
                        onRefreshForecast = onRefreshForecast
                )
            })

}