package com.uxstate.skycast.di

import android.app.Application
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.uxstate.skycast.data.local.converter.Converters
import com.uxstate.skycast.data.local.converter.JsonParserImpl
import com.uxstate.skycast.data.local.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideWeatherDatabase(appContext: Application, moshi: Moshi): WeatherDatabase {

        return Room.databaseBuilder(
                context = appContext,
                klass = WeatherDatabase::class.java,
                name = WeatherDatabase.DATABASE_NAME
        )
                .addTypeConverter(Converters(JsonParserImpl(moshi)))
                .fallbackToDestructiveMigration()
                .build()
    }


}