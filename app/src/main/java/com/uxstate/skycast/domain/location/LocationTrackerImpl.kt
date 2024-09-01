package com.uxstate.skycast.domain.location

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.utils.Resource
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationTrackerImpl
    @Inject
    constructor(
        private val locationClient: FusedLocationProviderClient,
        private val locationManager: LocationManager,
        private val application: Application,
    ) : LocationTracker {
        override suspend fun getCurrentLocation(): Resource<GeoPoint?> =
            suspendCancellableCoroutine { cont ->

                val priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY

                val isFineLocAccessGranted =
                    ContextCompat.checkSelfPermission(
                        application,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                    ) == PackageManager.PERMISSION_GRANTED

                val isCoarseLocPermissionGranted =
                    ContextCompat.checkSelfPermission(
                        application,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    ) == PackageManager.PERMISSION_GRANTED

                val isGpsEnabled =
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

                if (!isFineLocAccessGranted || !isCoarseLocPermissionGranted || !isGpsEnabled) {
                    Resource.Error(
                        data = null,
                        errorMessage = "Please make sure you have enabled location permission",
                    )
                }

                locationClient
                    .getCurrentLocation(
                        priority,
                        CancellationTokenSource().token,
                    ).apply {
                        addOnSuccessListener { location: Location? ->
                            if (location != null) {
                                cont.resume(
                                    Resource.Success(
                                        GeoPoint(
                                            latitude = location.latitude,
                                            longitude = location.longitude,
                                        ),
                                    ),
                                )
                            } else {
                                cont.resume(
                                    Resource.Success(
                                        data = null,
                                    ),
                                )
                            }
                        }

                        addOnFailureListener {
                            cont.resume(
                                Resource.Error(
                                    null,
                                    "Failed getting last known location",
                                ),
                            )
                        }

                        addOnCanceledListener {
                            cont.cancel()
                        }
                    }
            }
    }
