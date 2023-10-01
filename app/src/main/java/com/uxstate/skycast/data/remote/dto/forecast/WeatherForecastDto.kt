package com.uxstate.skycast.data.remote.dto.forecast


import com.squareup.moshi.Json

data class WeatherForecastDto(
    @Json(name = "city")
    val city: City,
    @Json(name = "cnt")
    val cnt: Int,
    @Json(name = "cod")
    val cod: String,
    @Json(name = "list")
    val list: List<WeatherData>,
    @Json(name = "message")
    val message: Int
)