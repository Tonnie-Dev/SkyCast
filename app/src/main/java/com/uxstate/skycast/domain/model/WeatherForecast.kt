package com.uxstate.skycast.domain.model

data class WeatherForecast(

    var date: String,
    val wind: Wind,
    val forecastWeatherDescription: List<WeatherDescription>,
    val forecastWeatherParams: WeatherParams
)