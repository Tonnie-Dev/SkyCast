package com.uxstate.skycast.presentation.forecast

sealed class ForecastEvent {
    data class OnDateChangeEvent(
        val date: Int,
    ) : ForecastEvent()
}
