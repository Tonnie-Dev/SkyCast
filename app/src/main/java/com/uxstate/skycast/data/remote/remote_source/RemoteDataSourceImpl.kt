package com.uxstate.skycast.data.remote.remote_source

import com.uxstate.skycast.data.remote.api.WeatherApi
import com.uxstate.skycast.data.remote.dto.current.CurrentWeatherDto
import com.uxstate.skycast.data.remote.dto.forecast.ForecastDataDto
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.utils.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: WeatherApi) : RemoteDataSource {


    // TODO: Add Explicit catch block
    override suspend fun getRemoteCurrentWeather(geoPoint: GeoPoint): Resource<CurrentWeatherDto> {


        return withContext(IO) {

            try {

                Timber.i("No Exception - inside try block")
                Resource.Success(
                        data = api.getCurrentWeather(
                                geoPoint.latitude,
                                geoPoint.longitude
                        )
                                .body()
                )
            } catch (e: Exception) {
                Timber.i("Exception - $e")
                Resource.Error(errorMessage = e.message ?: "Unknown Error Occurred")

            }
        }
    }

    // TODO: Add Explicit catch blocks
    override suspend fun getRemoteForecastWeather(cityId: Int): Resource<List<ForecastDataDto>> {
       return withContext(IO){

            try {
                    Resource.Success(data = api.getForecastWeather(cityId = cityId).body()?.list)
                    }
                    catch (e:Exception){

                     Resource.Error(errorMessage = e.message ?: "Unknown Error Occured")
                    }
       }
    }
}