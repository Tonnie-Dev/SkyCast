package com.uxstate.skycast.domain.repository

import com.uxstate.skycast.domain.model.CurrentWeather
import com.uxstate.skycast.domain.model.ForecastWeather
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getCurrentWeather(geoPoint: GeoPoint): Flow<Resource<CurrentWeather>>

    fun getForecastWeather(cityId: Int): Flow<Resource<List<ForecastWeather>>>
}
