package com.uxstate.skycast.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(

    val cityId: Int,
    val name: String,
    val wind: Wind,
    val lastFetchedTime: Long,
    val networkWeatherDescription: List<WeatherDescription>,
    val networkWeatherCondition: WeatherParams
) : Parcelable