package com.uxstate.skycast.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherParams(val temp: Double, val pressure: Double, val humidity: Double):Parcelable
