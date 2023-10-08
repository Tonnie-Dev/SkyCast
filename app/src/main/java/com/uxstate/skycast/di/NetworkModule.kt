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

    private const val READ_TIMEOUT_IN_SECONDS = 15L
    private const val CONNECT_TIMEOUT_IN_SECONDS = 15L

/*
    // log feature integrated to show request and response info.

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }*/


    //Connect Timeout - Time period for client to establish connection with the target host
    // Read Timeout - Max latency time for waiting server's response

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
                .addInterceptor { chain ->

                    /* val initialRequest = chain.request()
                     val initialUrl = initialRequest.url

                     val newUrl = initialUrl.newBuilder()
                             .addQueryParameter("appid", BuildConfig.API_KEY)
                             .build()

                     val newRequest = initialRequest.newBuilder().url(url = newUrl).build()

                     chain.proceed(newRequest)*/


                    chain.proceed(
                            chain.request()
                                    .apply {
                                        url.newBuilder()
                                                .addQueryParameter("appid", BuildConfig.API_KEY)
                                                .build()
                                    }


                    )
                }
                .apply {

                    connectTimeout(CONNECT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                    readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)

                }
                .build()
    }

   /* @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(CONNECT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build()*/

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




