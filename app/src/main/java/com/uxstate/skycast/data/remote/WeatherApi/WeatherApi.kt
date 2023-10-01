package com.uxstate.skycast.data.remote.WeatherApi

import com.uxstate.skycast.data.remote.dto.current.CurrentWeatherDto
import com.uxstate.skycast.data.remote.dto.forecast.WeatherForecastDto
import com.uxstate.skycast.utils.GET_CURRENT
import com.uxstate.skycast.utils.GET_FORECAST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(GET_CURRENT)
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Response<CurrentWeatherDto>

    @GET(GET_FORECAST)
    suspend fun getForecastWeather(@Query("id") cityId:Int):Response<WeatherForecastDto>
}