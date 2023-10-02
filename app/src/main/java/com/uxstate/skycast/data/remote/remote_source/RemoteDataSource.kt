package com.uxstate.skycast.data.remote.remote_source

import com.uxstate.skycast.data.remote.dto.current.CurrentWeatherDto
import com.uxstate.skycast.data.remote.dto.forecast.ForecastDataDto
import com.uxstate.skycast.domain.model.CurrentWeather
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.utils.Resource

interface RemoteDataSource {


    suspend fun getRemoteCurrentWeather(geoPoint: GeoPoint):Resource<CurrentWeatherDto>
    suspend fun getRemoteForecastWeather(cityId: Int):Resource<List<ForecastDataDto>>
}