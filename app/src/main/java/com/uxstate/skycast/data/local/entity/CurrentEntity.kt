package com.uxstate.skycast.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uxstate.skycast.domain.model.WeatherCondition
import com.uxstate.skycast.domain.model.WeatherDescription
import com.uxstate.skycast.domain.model.Wind

@Entity
data class CurrentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val lastFetchTime: Long,
    val cityName: String,
    val cityId: Int,
    val weatherDesc: List<WeatherDescription>,
    @Embedded val wind: Wind,
    @Embedded val weatherCondition: WeatherCondition
)
