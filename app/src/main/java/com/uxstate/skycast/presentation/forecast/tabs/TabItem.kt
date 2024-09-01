package com.uxstate.skycast.presentation.forecast.tabs

import com.uxstate.skycast.presentation.forecast.ForecastState
import com.uxstate.skycast.utils.shortDate
import com.uxstate.skycast.utils.shortDayOfWeek

sealed class TabItem(
    val dayOfWeek: String,
    val dayOfTheMonth: String,
) {
    data class DayOne(
        val state: ForecastState,
        val onRefreshForecast: () -> Unit,
        val page: Int,
    ) : TabItem(
            dayOfWeek = shortDayOfWeek(page.toLong()),
            dayOfTheMonth = shortDate(page.toLong()),
        )
}
