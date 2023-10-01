package com.uxstate.skycast.data.remote.dto.forecast


import com.squareup.moshi.Json

data class ForecastWeatherDto(
    @Json(name = "city")
    val city: City,
    @Json(name = "cnt")
    val cnt: Int,
    @Json(name = "cod")
    val cod: String,
    @Json(name = "list")
    val list: List<ForecastDataDto>,
    @Json(name = "message")
    val message: Int
)