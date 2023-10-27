package com.uxstate.skycast.presentation.home

sealed class HomeEvent {

    data object GpsEnabledEvent:HomeEvent()
    data object GpsDisabledEvent:HomeEvent()
}