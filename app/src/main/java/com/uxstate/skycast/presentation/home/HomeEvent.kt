package com.uxstate.skycast.presentation.home

sealed class HomeEvent {


    data object OnRetry:HomeEvent()
    data object OnDismissDialog:HomeEvent()
    data object OnConfirmDialog:HomeEvent()
    data object OnCancelDialog:HomeEvent()

}