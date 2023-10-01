package com.uxstate.skycast.data.remote.dto.forecast


import com.squareup.moshi.Json

data class ForecastWindDto(
    @Json(name = "deg")
    val deg: Int,
    @Json(name = "gust")
    val gust: Double,
    @Json(name = "speed")
    val speed: Double
)