package com.uxstate.skycast.domain.connectivity

import android.net.ConnectivityManager
import kotlinx.coroutines.flow.Flow


interface ConnectivityObserver {

    fun observe(): Flow<Status>
    fun isInternetConnectionAvailable( status: Status):Boolean
    enum class Status {

        AVAILABLE, UNAVAILABLE, LOSING, LOST
    }
}