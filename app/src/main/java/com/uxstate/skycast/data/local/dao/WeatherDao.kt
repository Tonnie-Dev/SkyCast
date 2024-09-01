package com.uxstate.skycast.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uxstate.skycast.data.local.entity.CurrentEntity
import com.uxstate.skycast.data.local.entity.ForecastEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentData(currentData: CurrentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastData(forecastData: List<ForecastEntity>)

    @Query("DELETE FROM current_weather_table")
    suspend fun clearCurrentWeatherData()

    @Query("DELETE FROM forecast_weather_table")
    suspend fun clearForecastWeatherData()

    @Query("SELECT * FROM current_weather_table")
    suspend fun getCurrentWeather(): CurrentEntity?

    @Query("SELECT* FROM forecast_weather_table")
    suspend fun getForecastWeather(): List<ForecastEntity>?
}
