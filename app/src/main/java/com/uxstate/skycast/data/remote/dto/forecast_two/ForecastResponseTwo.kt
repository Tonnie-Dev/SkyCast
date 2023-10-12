package com.uxstate.skycast.data.remote.dto.forecast_two


import com.squareup.moshi.Json

data class ForecastResponseTwo(
    @Json(name = "city")
    val city: City,
    @Json(name = "cnt")
    val cnt: Int,
    @Json(name = "cod")
    val cod: String,
    @Json(name = "list")
    val list: List<>,
    @Json(name = "message")
    val message: Int
)