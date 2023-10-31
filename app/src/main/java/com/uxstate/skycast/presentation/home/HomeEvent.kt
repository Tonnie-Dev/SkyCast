package com.uxstate.skycast.presentation.home

sealed class HomeEvent {



    data object OnDismissDialog : HomeEvent()
    data object OnCancelDialog : HomeEvent()
    data object OnConfirmDialog : HomeEvent()
    data object OnContinue : HomeEvent()
    data object OnExit : HomeEvent()

}