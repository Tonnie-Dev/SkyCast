package com.uxstate.skycast.di

import android.app.Application
import androidx.room.Room
import com.uxstate.skycast.data.local.converter.JsonParser
import com.uxstate.skycast.data.local.converter.JsonParserImpl
import com.uxstate.skycast.data.local.db.WeatherDatabase
import com.uxstate.skycast.data.local.localsource.LocalDataSource
import com.uxstate.skycast.data.local.localsource.LocalDataSourceImpl
import com.uxstate.skycast.data.remote.remotesource.RemoteDataSource
import com.uxstate.skycast.data.remote.remotesource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {
    @Binds
    abstract fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun provideJsonParser(jsonParserImp: JsonParserImpl): JsonParser
}

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Singleton
    @Provides
    fun provideWeatherDatabase(appContext: Application): WeatherDatabase =
        Room
            .databaseBuilder(
                context = appContext,
                klass = WeatherDatabase::class.java,
                name = WeatherDatabase.DATABASE_NAME,
            ).fallbackToDestructiveMigration()
            .build()
}
