package com.uxstate.skycast.domain.model

import com.uxstate.skycast.BuildConfig

data class GeoPoint(
    val latitude: Double,
    val longitude: Double,
) {
    fun someFunction() {
        val apiKey = BuildConfig.API_KEY
    }
}
