package com.uxstate.skycast.domain.connectivity

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConnectivityObserverImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : ConnectivityObserver {
    /*
        private val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager*/


    override fun observe(): Flow<ConnectivityObserver.Status> {

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


            connectivityManager.registerDefaultNetworkCallback(callback)

            //unregister callback - triggered when flow is cancelled
            awaitClose {

                connectivityManager.unregisterNetworkCallback(callback)
            }
            //return distinct flow enum values - ignores consecutive same enum values
        }.distinctUntilChanged()
    }
}