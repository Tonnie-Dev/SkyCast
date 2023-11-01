package com.uxstate.skycast.presentation.home

import android.content.Context

sealed class HomeEvent {

    data object OnConfirmDialog : HomeEvent()
    data object OnCancelDialog : HomeEvent()
    data object OnDismissDialog : HomeEvent()
    data object OnContinue : HomeEvent()
    data class OnExit(val context: Context) : HomeEvent()


}