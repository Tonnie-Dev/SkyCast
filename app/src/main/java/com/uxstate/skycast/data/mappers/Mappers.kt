package com.uxstate.skycast.data.mappers

import com.uxstate.skycast.data.local.entity.CurrentEntity
import com.uxstate.skycast.data.local.entity.ForecastEntity
import com.uxstate.skycast.data.remote.dto.current.CurrentWeatherDto
import com.uxstate.skycast.data.remote.dto.current.CurrentWeatherDescDto
import com.uxstate.skycast.data.remote.dto.current.WindDto
import com.uxstate.skycast.data.remote.dto.forecast.ForecastDataDto
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
                    pressure = this.main.pressure.toDouble(),
                    humidity = this.main.humidity.toDouble()
            )
    )
}



fun ForecastDataDto.toEntity(lastFetchTime: Long): ForecastEntity {

    return ForecastEntity(

            date = this.dtTxt,
            lastFetchTime = lastFetchTime,
            networkWeatherDesc = this.forecastWeatherDescDto,
            wind = this.wind,
            networkWeatherParams = WeatherParams(
                    temp = 30.31,
                    pressure = 32.33,
                    humidity = 34.35
            )
    )
}
fun CurrentWeatherDescDto.toModel(): WeatherDescription = WeatherDescription(
        id = this.id,
        main = this.main,
        description = this.description,
        icon = this.icon
)

fun WindDto.toModel(): Wind = Wind(speed = this.speed, deg = this.deg)