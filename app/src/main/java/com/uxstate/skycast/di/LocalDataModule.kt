package com.uxstate.skycast.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.uxstate.skycast.data.local.db.WeatherDatabase
import com.uxstate.skycast.data.local.local_source.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Singleton
    @Provides
    fun provideWeatherDatabase(appContext: Application): WeatherDatabase {

        return Room.databaseBuilder(
                context = appContext,
                klass = WeatherDatabase::class.java,
                name = WeatherDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }


}