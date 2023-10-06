package com.uxstate.skycast.data.repository

import com.uxstate.skycast.data.local.local_source.LocalDataSource
import com.uxstate.skycast.data.remote.remote_source.RemoteDataSource
import com.uxstate.skycast.domain.model.CurrentWeather
import com.uxstate.skycast.domain.model.ForecastWeather
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.domain.repository.WeatherRepository
import com.uxstate.skycast.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : WeatherRepository {
    override fun getCurrentWeather(geoPoint: GeoPoint): Flow<Resource<CurrentWeather>> {
        TODO("Not yet implemented")
    }

    override fun getForecastWeather(cityId: Int): Flow<Resource<List<ForecastWeather>>> {
        TODO("Not yet implemented")
    }
}