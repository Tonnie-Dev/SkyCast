package com.uxstate.skycast.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentWeather(
    val cityId: Int,
    val cityName: String,
    val wind: Wind,
    val lastFetchedTime: Long,
    val networkWeatherDescription: List<WeatherDescription>,
    val networkWeatherCondition: WeatherParams,
) : Parcelable
