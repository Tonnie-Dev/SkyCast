package com.uxstate.skycast.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uxstate.skycast.domain.model.WeatherDescription
import com.uxstate.skycast.domain.model.WeatherParams
import com.uxstate.skycast.domain.model.Wind

@Entity(tableName = "current_weather_table")
data class CurrentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val lastFetchTime: Long,
    val cityName: String,
    val cityId: Int,
    val weatherDesc: List<WeatherDescription>,
    @Embedded val wind: Wind,
    @Embedded val weatherParams: WeatherParams,
)
