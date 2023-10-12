package com.uxstate.skycast.data.remote.dto.current


import com.squareup.moshi.Json

data class CurrentWeatherDto(
    @Json(name = "base")
    val base: String,
    @Json(name = "clouds")
    val clouds: Clouds,
    @Json(name = "cod")
    val cod: Int,
    @Json(name = "coord")
    val coord: Coord,
    @Json(name = "dt")
    val dt: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "main")
    val main: Main,
    @Json(name = "name")
    val cityName: String,
    @Json(name = "sys")
    val sys: Sys,
    @Json(name = "timezone")
    val timezone: Int,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "forecastWeatherDescDto")
    val weather: List<CurrentWeatherDescDto>,
    @Json(name = "wind")
    val wind: CurrentWindDto
)