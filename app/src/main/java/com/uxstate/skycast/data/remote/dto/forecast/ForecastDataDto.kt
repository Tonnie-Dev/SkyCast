package com.uxstate.skycast.data.remote.dto.forecast


import com.squareup.moshi.Json

data class ForecastDataDto (
    @Json(name = "clouds")
    val clouds: Clouds,
    @Json(name = "dt")
    val dt: Int,
    @Json(name = "dt_txt")
    val dtTxt: String,
    @Json(name = "main")
    val main: Main,
    @Json(name = "pop")
    val pop: Double,
    @Json(name = "rain")
    val rain: Rain,
    @Json(name = "sys")
    val sys: Sys,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "weather")
    val forecastWeatherDescDto: List<ForecastWeatherDescDto>,
    @Json(name = "wind")
    val wind: Wind
)