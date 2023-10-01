package com.uxstate.skycast.data.mappers

import com.uxstate.skycast.data.local.entity.CurrentEntity
import com.uxstate.skycast.data.local.entity.ForecastEntity
import com.uxstate.skycast.data.remote.dto.current.CurrentWeatherDto
import com.uxstate.skycast.data.remote.dto.current.CurrentWeatherDescDto
import com.uxstate.skycast.data.remote.dto.current.CurrentWindDto
import com.uxstate.skycast.data.remote.dto.forecast.ForecastDataDto
import com.uxstate.skycast.data.remote.dto.forecast.ForecastWeatherDescDto
import com.uxstate.skycast.data.remote.dto.forecast.ForecastWindDto
import com.uxstate.skycast.domain.model.WeatherParams
import com.uxstate.skycast.domain.model.WeatherDescription
import com.uxstate.skycast.domain.model.Wind

fun CurrentWeatherDto.toEntity(lastFetchTime: Long): CurrentEntity {

    return CurrentEntity(
            lastFetchTime = lastFetchTime,
            cityName = this.name,
            cityId = this.id,
            weatherDesc = this.weather.map { it.toModel() },
            wind = this.wind.toModel(),
            weatherParams = WeatherParams(
                    temp = this.main.temp,

                   // TODO: check if these need to be doubles or int
                    pressure = this.main.pressure.toDouble(),
                    humidity = this.main.humidity.toDouble()
            )
    )
}



fun ForecastDataDto.toEntity(lastFetchTime: Long): ForecastEntity {

    return ForecastEntity(

            date = this.dtTxt,
            lastFetchTime = lastFetchTime,
            forecastWeatherDescription = this.forecastWeatherDescDto.map { it.toModel() },
            wind = this.forecastWindDto.toModel(),
            forecastWeatherParams = WeatherParams(
                    temp = this.main.temp,

                    // TODO: check if these need to be doubles or int
                    pressure = this.main.pressure.toDouble(),
                    humidity = this.main.humidity.toDouble()
            )
    )
}
fun CurrentWeatherDescDto.toModel(): WeatherDescription = WeatherDescription(
        id = this.id,
        main = this.main,
        description = this.description,
        icon = this.icon
)

fun CurrentWindDto.toModel(): Wind = Wind(speed = this.speed, deg = this.deg)

fun ForecastWeatherDescDto.toModel(): WeatherDescription =  WeatherDescription(
id = this.id,
main = this.main,
description = this.description,
icon = this.icon
)

fun ForecastWindDto.toModel(): Wind = Wind(speed = this.speed, deg = this.deg)