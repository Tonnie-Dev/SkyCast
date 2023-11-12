package com.uxstate.skycast.domain.connectivity

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConnectivityObserverImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : ConnectivityObserver {


    override fun observe(): Flow<ConnectivityObserver.Status> {
        val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()
        // needs ACCESS_NETWORK_STATE Permission on Manifest

        //anonymously introducing a call back
        return callbackFlow {

            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)

                    launch { send(ConnectivityObserver.Status.AVAILABLE) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)

                    launch { send(ConnectivityObserver.Status.LOSING) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)

                    launch { send(ConnectivityObserver.Status.LOST) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.Status.UNAVAILABLE) }
                }
            }

            connectivityManager.registerNetworkCallback(networkRequest, callback)

            //unregister callback - triggered when flow is cancelled
            awaitClose {

                connectivityManager.unregisterNetworkCallback(callback)
            }

            //return distinct flow enum values - ignores consecutive same enum values
        }.distinctUntilChanged()
    }

    override fun isInternetConnectionAvailable(status: ConnectivityObserver.Status): Boolean {

        val activeNetwork = connectivityManager.activeNetwork
        val caps = connectivityManager.getNetworkCapabilities(activeNetwork)

        return when (activeNetwork) {
            null -> false
            else -> status == ConnectivityObserver.Status.AVAILABLE
                    &&
                    (caps?.hasCapability(NET_CAPABILITY_VALIDATED) ?: false)
        }


    }
}
