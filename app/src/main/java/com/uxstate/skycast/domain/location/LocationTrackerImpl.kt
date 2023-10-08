package com.uxstate.skycast.domain.location

import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.utils.Resource

class LocationTrackerImpl : LocationTracker {
    override suspend fun getCurrentLocation(): Resource<GeoPoint?> {
        TODO("Not yet implemented")
    }
}