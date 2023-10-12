package com.uxstate.skycast.data.remote.dto.forecast_two


import com.squareup.moshi.Json

data class Sys(
    @Json(name = "pod")
    val pod: String
)