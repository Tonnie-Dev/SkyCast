package com.uxstate.skycast.presentation.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.util.Log
import timber.log.Timber

class LocationProviderChangedReceiver : BroadcastReceiver() {

    private var isGpsEnabled: Boolean = false
    private var isNetworkEnabled: Boolean = false
    private var locationListener: LocationListener? = null

    fun init(locationListener: LocationListener) {
        this.locationListener = locationListener
    }//Set the callbacks before registering

    override fun onReceive(context: Context, intent: Intent) {
        intent.action?.let { act ->
            if (act.matches("android.location.PROVIDERS_CHANGED".toRegex())) {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                Timber.i("Location Providers changed, is GPS Enabled: $isGpsEnabled")
               // Log.i(TAG, "Location Providers changed, is GPS Enabled: $isGpsEnabled")

                if (isGpsEnabled && isNetworkEnabled) {
                    locationListener?.onEnabled()
                } else {
                    locationListener?.onDisabled()
                }
            }
        }
    }

    interface LocationListener {//We need some callback methods
    fun onEnabled()
        fun onDisabled()
    }

    companion object {
        //private val TAG = "Location"
    }
}