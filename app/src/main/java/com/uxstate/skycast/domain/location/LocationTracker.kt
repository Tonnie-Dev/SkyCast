package com.uxstate.skycast.domain.location

import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.utils.Resource

interface LocationTracker {

    suspend fun getCurrentLocation():Resource<GeoPoint?>
}