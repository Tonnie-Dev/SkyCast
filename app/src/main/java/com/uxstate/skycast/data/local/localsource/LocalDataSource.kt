package com.uxstate.skycast.data.local.localsource

import com.uxstate.skycast.data.local.entity.CurrentEntity
import com.uxstate.skycast.data.local.entity.ForecastEntity

interface LocalDataSource {
    suspend fun insertCurrentWeather(currentEntity: CurrentEntity)

    suspend fun insertForecastWeather(forecastEntity: List<ForecastEntity>)

    suspend fun getCurrentWeather(): CurrentEntity?

    suspend fun getForecastWeather(): List<ForecastEntity>?

    suspend fun clearCurrentWeatherData()

    suspend fun clearForecastWeatherData()
}
