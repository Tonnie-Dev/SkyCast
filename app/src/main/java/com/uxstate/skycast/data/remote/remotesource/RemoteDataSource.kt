package com.uxstate.skycast.data.remote.remotesource

import com.uxstate.skycast.data.remote.dto.current.CurrentWeatherDto
import com.uxstate.skycast.data.remote.dto.forecast.ForecastDataDto
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.utils.Resource

interface RemoteDataSource {
    suspend fun getRemoteCurrentWeather(geoPoint: GeoPoint): Resource<CurrentWeatherDto>

    suspend fun getRemoteForecastWeather(cityId: Int): Resource<List<ForecastDataDto>>
}
