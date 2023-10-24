package com.uxstate.skycast.domain.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.utils.Resource
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationTrackerImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
) :
    LocationTracker {
    override suspend fun getCurrentLocation(): Resource<GeoPoint?> =
        suspendCancellableCoroutine { cancellableContinuation ->

        val isFineLocAccessGranted = ContextCompat.checkSelfPermission(
                    application,
                    Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


            val isCoarseLocPermissionGranted =
                ContextCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


            val locationManager =
                application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val isGpsEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)



            if (!isFineLocAccessGranted || !isCoarseLocPermissionGranted || !isGpsEnabled) {

               Resource.Error(
                        data = null,
                        errorMessage = "Please make sure you have enabled location permission"
                )
                Timber.i("if-Block activated")
                //cancellableContinuation.cancel()
            }

            //locationClient.lastLocation returns a task

            locationClient.lastLocation
                    .apply {

                addOnSuccessListener {
                    Timber.i("On Success called")
                    it?.let {
                        Timber.i("Non-Null Location")
                        cancellableContinuation.resume(
                                Resource.Success(
                                        GeoPoint(
                                                latitude = it.latitude,
                                                longitude = it.longitude
                                        )
                                )
                        )
                    } ?: run {
                        Timber.i("Null Location inside sec run{}")

                        cancellableContinuation.resume( Resource.Error(
                                data = null,
                                errorMessage = "Please make sure you have enabled location permission"
                        ))

                        Timber.i("Cancellable Continuation Called")
                        //cancellableContinuation.cancel()

                         }

                    Timber.i("Exiting Success Listener")

                }
                        Timber.i("near-failure")
                addOnFailureListener {

                    Timber.i("On Failure called")
                    cancellableContinuation.resume(
                            Resource.Error(
                                    errorMessage = "Failed getting the last known location"
                            )
                    )
                }

                addOnCanceledListener {
                    Timber.i("Cancellable called")
                    cancellableContinuation.cancel()
                }
            }

            Timber.i("getLoc()")
        }


}