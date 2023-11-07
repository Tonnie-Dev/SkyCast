package com.uxstate.skycast.presentation.forecast

import androidx.compose.runtime.Composable
import java.time.LocalDate

sealed class TabItem(
    val dayOfWeek: String,
    val dayOfTheMonth: String,
    val content: @Composable () -> Unit
) {

    data object DayOne:TabItem(dayOfWeek = LocalDate.now().dayOfWeek.name.substring(0..2), dayOfTheMonth = LocalDate.now().)
}