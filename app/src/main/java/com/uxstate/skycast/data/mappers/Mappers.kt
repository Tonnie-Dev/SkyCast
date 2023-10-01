package com.uxstate.skycast.data.mappers

import com.uxstate.skycast.data.local.entity.CurrentEntity
import com.uxstate.skycast.data.remote.dto.current.CurrentWeatherDto
import com.uxstate.skycast.data.remote.dto.current.WeatherDescriptionDto
import com.uxstate.skycast.domain.model.WeatherCondition
import com.uxstate.skycast.domain.model.WeatherDescription
import com.uxstate.skycast.domain.model.Wind

fun CurrentWeatherDto.toEntity(lastFetchTime:Long):CurrentEntity {

    return CurrentEntity(
            lastFetchTime = lastFetchTime,
            cityName = this.name,
            cityId = this.id,
            weatherDesc = this.weather.map { it.toModel() },
            wind =this.wind,
            weatherCondition = WeatherCondition(
                    temp = this.main.temp,
                    pressure = this.main.pressure.toDouble(),
                    humidity = this.main.humidity.toDouble()
            )
    )
}


fun WeatherDescriptionDto.toModel():WeatherDescription = WeatherDescription(
        id = this.id,
        main = this.main,
        description = this.description,
        icon = this.icon
)