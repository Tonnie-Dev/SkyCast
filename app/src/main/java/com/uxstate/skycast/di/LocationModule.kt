package com.uxstate.skycast.di

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.LocationServices
import com.uxstate.skycast.domain.location.LocationTracker
import com.uxstate.skycast.domain.location.LocationTrackerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(locationTrackerImpl: LocationTrackerImpl): LocationTracker

    companion object {

        @Provides
        @Singleton
        fun provideFusedLocationProviderClient(@ApplicationContext context: Context) =
            LocationServices.getFusedLocationProviderClient(context)

        @Provides
        @Singleton

        fun provideLocationManager(@ApplicationContext context: Context) =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}