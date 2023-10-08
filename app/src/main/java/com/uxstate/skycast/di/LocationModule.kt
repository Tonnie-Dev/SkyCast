package com.uxstate.skycast.di

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.uxstate.skycast.domain.location.LocationTracker
import com.uxstate.skycast.domain.location.LocationTrackerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(locationTrackerImpl: LocationTrackerImpl): LocationTracker

    companion object {

        @Provides
        @Singleton
        fun provideFusedLocationProviderClient(@ApplicationContext context: Context) =
            LocationServices.getFusedLocationProviderClient(context)
    }
}