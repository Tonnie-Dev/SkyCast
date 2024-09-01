package com.uxstate.skycast.data.remote.dto.forecast

import com.squareup.moshi.Json

data class Rain(
    @Json(name = "rain")
    val rain: Double,
)
