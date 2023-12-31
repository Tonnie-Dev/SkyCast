package com.uxstate.skycast.data.repository

import com.uxstate.skycast.data.local.entity.CurrentEntity
import com.uxstate.skycast.data.local.entity.ForecastEntity
import com.uxstate.skycast.data.local.local_source.LocalDataSource
import com.uxstate.skycast.data.mappers.toEntity
import com.uxstate.skycast.data.mappers.toModel
import com.uxstate.skycast.data.remote.remote_source.RemoteDataSource
import com.uxstate.skycast.domain.model.CurrentWeather
import com.uxstate.skycast.domain.model.ForecastWeather
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.domain.repository.WeatherRepository
import com.uxstate.skycast.utils.EXPIRY_TIME
import com.uxstate.skycast.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : WeatherRepository {
    override fun getCurrentWeather(
        geoPoint: GeoPoint
    ): Flow<Resource<CurrentWeather>> = flow {
        
        emit(Resource.Loading(isLoading = true))

        fetchLocalCurrentWeather()?.takeIf {

            !it.isExpired()

        }
                ?.let {


                    emit(Resource.Success(it.toModel()))

                } ?: run {

            when (val result = remoteDataSource.getRemoteCurrentWeather(geoPoint)) {

                is Resource.Success -> {

                    localDataSource.clearCurrentWeatherData()
                    result.data?.let {
                        localDataSource.insertCurrentWeather(it.toEntity(System.currentTimeMillis()))
                    }

                    emit(Resource.Success(fetchLocalCurrentWeather()?.toModel()))
                }

                is Resource.Error -> {


                    emit(
                            Resource.Error(
                                    data = fetchLocalCurrentWeather()?.toModel(),
                                    errorMessage = result.errorMessage ?: "Unknown Error"
                            )
                    )
                }

                else -> {

                    emit(Resource.Loading(isLoading = true))
                }
            }
        }
        emit(Resource.Loading(isLoading = false))

    }

    override fun getForecastWeather(
        cityId: Int
    ): Flow<Resource<List<ForecastWeather>>> = flow {

        emit(Resource.Loading(isLoading = true))

        fetchLocalForecastWeather()?.takeIf { !it.isExpired() && it.isNotEmpty() }
                ?.let {

                    emit(Resource.Success(data = it.map { entity -> entity.toModel() }))
                }
            ?: run {


                when (val result = remoteDataSource.getRemoteForecastWeather(cityId = cityId)) {

                    is Resource.Success -> {


                        localDataSource.clearForecastWeatherData()

                        result.data?.let { data ->
                            localDataSource.insertForecastWeather(data.map { it.toEntity(System.currentTimeMillis()) })
                        }

                        emit(Resource.Success(data = fetchLocalForecastWeather()?.map { it.toModel() }))

                    }

                    is Resource.Error -> {

                        emit(
                                Resource.Error(
                                        data = fetchLocalForecastWeather()?.map { it.toModel() },
                                        errorMessage = result.errorMessage ?: "Unknown Error"
                                )
                        )

                    }

                    else -> {

                        emit(Resource.Loading())
                    }
                }

            }

        emit(Resource.Loading(isLoading = false))
    }

    private suspend fun fetchLocalCurrentWeather(): CurrentEntity? {
        return localDataSource.getCurrentWeather()
    }

    private suspend fun fetchLocalForecastWeather(): List<ForecastEntity>? {


        return localDataSource.getForecastWeather()
    }

    private fun CurrentEntity.isExpired(): Boolean {

        return System.currentTimeMillis() - this.lastFetchTime > EXPIRY_TIME
    }

    private fun List<ForecastEntity>.isExpired(): Boolean {

        return if (isEmpty()) true else System.currentTimeMillis() - this.first().lastFetchTime > EXPIRY_TIME
    }


    private fun fetchRemoteCurrentWeather(geoPoint: GeoPoint): Flow<Resource<CurrentWeather>> =
        flow {

            emit(Resource.Loading(isLoading = true))
            when (val result = remoteDataSource.getRemoteCurrentWeather(geoPoint)) {


                is Resource.Success -> {

                    result.data?.let {

                        val currentWeatherData = it.toEntity(System.currentTimeMillis())
                        localDataSource.insertCurrentWeather(currentWeatherData)


                    }

                    emit(Resource.Success(data = fetchLocalCurrentWeather()?.toModel()))
                }

                is Resource.Error -> {

                    emit(
                            Resource.Error(
                                    data = fetchLocalCurrentWeather()?.toModel(),
                                    errorMessage = result.errorMessage ?: "Unknown Error"
                            )
                    )
                }

                else -> {

                    emit(Resource.Loading(isLoading = true))
                }
            }

            emit(Resource.Loading(isLoading = false))
        }


    private fun getRemoteForecastWeather(
        cityId: Int,
        isFetchFromRemote: Boolean
    ): Flow<Resource<List<ForecastWeather>>> = flow {


        emit(Resource.Loading(isLoading = true))

        when (val result = remoteDataSource.getRemoteForecastWeather(cityId)) {

            is Resource.Success -> {
                //localDataSource.clearForecastWeatherData()

                result.data?.let { data ->

                    localDataSource.insertForecastWeather(data.map { it.toEntity(System.currentTimeMillis()) })
                }
            }

            is Resource.Error -> {

                emit(
                        Resource.Error(
                                data = fetchLocalForecastWeather()?.map { it.toModel() },
                                errorMessage = result.errorMessage ?: "Unknown Error"
                        )
                )
            }

            is Resource.Loading -> {

                emit(Resource.Loading(isLoading = true))
            }
        }

        emit(Resource.Loading(isLoading = false))
    }


}