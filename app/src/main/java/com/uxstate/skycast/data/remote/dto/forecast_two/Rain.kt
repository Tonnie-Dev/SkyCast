package com.uxstate.skycast.data.remote.dto.forecast_two


import com.squareup.moshi.Json

data class Rain(
    @Json(name = "3h")
    val h: Double
)