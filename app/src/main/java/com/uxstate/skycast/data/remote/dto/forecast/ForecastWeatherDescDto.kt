package com.uxstate.skycast.data.remote.dto.forecast

import com.squareup.moshi.Json

data class ForecastWeatherDescDto(
    @Json(name = "description")
    val description: String,
    @Json(name = "icon")
    val icon: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "main")
    val main: String,
)
