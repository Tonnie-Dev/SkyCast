package com.uxstate.skycast.domain.model

data class ForecastWeather(
    var date: String,
    val wind: Wind,
    val forecastWeatherDescription: List<WeatherDescription>,
    val forecastWeatherParams: WeatherParams,
)
