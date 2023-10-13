package com.uxstate.skycast.data.remote.remote_source

import com.uxstate.skycast.data.remote.api.WeatherApi
import com.uxstate.skycast.data.remote.dto.current.CurrentWeatherDto
import com.uxstate.skycast.data.remote.dto.forecast.ForecastDataDto
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.utils.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: WeatherApi) : RemoteDataSource {


    // TODO: Add Explicit catch block
    override suspend fun getRemoteCurrentWeather(geoPoint: GeoPoint): Resource<CurrentWeatherDto> {
        Timber.i("Current Weather F1 called")

        return withContext(IO) {

            try {

                Timber.i("RDS Success ${api.getCurrentWeather(
                        geoPoint.latitude,
                        geoPoint.longitude
                )
                        .body()}")

                Resource.Success(
                        data = api.getCurrentWeather(
                                geoPoint.latitude,
                                geoPoint.longitude
                        )
                                .body()
                )
            } catch (e: Exception) {
                Timber.i("RDS Error - ${e.message}")
                Resource.Error(errorMessage = e.message ?: "Unknown Error Occurred")

            }
        }
    }

    // TODO: Add Explicit catch blocks
    override suspend fun getRemoteForecastWeather(cityId: Int): Resource<List<ForecastDataDto>> {

        Timber.i("Forecast Weather F2 called")
       return withContext(IO){

            try {
                Timber.i("getRemoteForecastWeather() called with Success -  ${api.getForecastWeather(cityId = cityId).myList}")
                    Resource.Success(data = api.getForecastWeather(cityId = cityId).myList)
                    }
                    catch (e: IOException){
                        Timber.i("F2 called with Error - $e ${e.message}")
                     Resource.Error(errorMessage = e.message ?: "Unknown Error Occured")
                    }
       }
    }
}