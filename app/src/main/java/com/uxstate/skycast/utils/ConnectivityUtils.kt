package com.uxstate.skycast.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_NOT_VPN
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import com.uxstate.skycast.domain.connectivity.ConnectivityObserver


fun ConnectivityManager.isConnectionAvailable(status: ConnectivityObserver.Status): Boolean {

    val caps = getNetworkCapabilities(activeNetwork)!!

    return (caps.hasCapability(NET_CAPABILITY_VALIDATED)
            &&
            caps.hasCapability(NET_CAPABILITY_NOT_VPN)
            &&
            status == ConnectivityObserver.Status.AVAILABLE)
}