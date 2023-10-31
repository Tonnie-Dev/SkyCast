package com.uxstate.skycast.presentation.home

import android.content.Context

sealed class HomeEvent {


    data object OnContinue : HomeEvent()
    data class OnExit(val context: Context) : HomeEvent()

}