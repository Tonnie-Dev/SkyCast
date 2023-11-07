package com.uxstate.skycast.presentation.forecast

import androidx.compose.runtime.Composable
import java.time.LocalDate

sealed class TabItem(
    val dayOfWeek: String,
    val dayOfTheMonth: String,
    val content: @Composable () -> Unit
) {

    data object DayOne : TabItem(
            dayOfWeek = LocalDate.now().dayOfWeek.name.substring(0..2),
            dayOfTheMonth = LocalDate.now().dayOfMonth.toString() + " " +
                    LocalDate.now().month.name.substring(0..2),
            content = {})

    data object DayTwo : TabItem(
            dayOfWeek = LocalDate.now()
                    .plusDays(1).dayOfWeek.name.substring(0..2),
            dayOfTheMonth = LocalDate.now()
                    .plusDays(1).dayOfMonth.toString() + " " +
                    LocalDate.now()
                            .plusDays(1).month.name.substring(0..2),
            content = {})

    data object DayThree : TabItem(
            dayOfWeek = LocalDate.now()
                    .plusDays(2).dayOfWeek.name.substring(0..2),
            dayOfTheMonth = LocalDate.now()
                    .plusDays(2).dayOfMonth.toString() + " " +
                    LocalDate.now()
                            .plusDays(2).month.name.substring(0..2),
            content = {})
}