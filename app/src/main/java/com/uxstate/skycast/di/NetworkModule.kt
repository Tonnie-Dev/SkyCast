package com.uxstate.skycast.di


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.uxstate.skycast.BuildConfig
import com.uxstate.skycast.data.remote.api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val READ_TIMEOUT_IN_SECONDS = 10L
    private const val CONNECT_TIMEOUT_IN_SECONDS = 10L

    //Connect Timeout - Time period for client to establish connection with the target host
    // Read Timeout - Max latency time for waiting server's response

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
                .addInterceptor { chain ->

                 val originalRequest = chain.request()
                    val newRequest = originalRequest.newBuilder()
                            .apply {
                                url(originalRequest.url.newBuilder()
                                        .addQueryParameter("appid", BuildConfig.API_KEY)
                                        .build())
                            }
                            .build()

                     chain.proceed(newRequest)


                }
                .apply {

                    connectTimeout(CONNECT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                    readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)

                }
                .build()
    }


    @Provides
    @Singleton
    fun provideMoshiConverter(): Moshi {


        return Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(moshi: Moshi, client: OkHttpClient): WeatherApi =
        Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client)
                .build()
                .create()


}


