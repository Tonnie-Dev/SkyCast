package com.uxstate.skycast.data.local.localsource

import com.uxstate.skycast.data.local.db.WeatherDatabase
import com.uxstate.skycast.data.local.entity.CurrentEntity
import com.uxstate.skycast.data.local.entity.ForecastEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl
    @Inject
    constructor(
        database: WeatherDatabase,
    ) : LocalDataSource {
        private val dao = database.dao

        override suspend fun insertCurrentWeather(currentEntity: CurrentEntity) {
            withContext(Dispatchers.IO) {
                dao.insertCurrentData(currentEntity)
            }
        }

        override suspend fun insertForecastWeather(forecastData: List<ForecastEntity>) {
            withContext(Dispatchers.IO) {
                dao.insertForecastData(forecastData)
            }
        }

        override suspend fun getCurrentWeather(): CurrentEntity? = withContext(Dispatchers.IO) { dao.getCurrentWeather() }

        override suspend fun getForecastWeather(): List<ForecastEntity>? = withContext(Dispatchers.IO) { dao.getForecastWeather() }

        override suspend fun clearCurrentWeatherData() {
            withContext(Dispatchers.IO) { dao.clearCurrentWeatherData() }
        }

        override suspend fun clearForecastWeatherData() {
            withContext(Dispatchers.IO) { dao.clearForecastWeatherData() }
        }
    }
