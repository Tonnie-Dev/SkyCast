package com.uxstate.skycast.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uxstate.skycast.domain.model.WeatherDescription
import com.uxstate.skycast.domain.model.WeatherParams
import com.uxstate.skycast.domain.model.Wind

@Entity(tableName = "forecast_weather_table")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val date: String,
    val lastFetchTime: Long,
    val forecastWeatherDescription: List<WeatherDescription>,
    @Embedded val wind: Wind,
    @Embedded val forecastWeatherParams: WeatherParams,
)
