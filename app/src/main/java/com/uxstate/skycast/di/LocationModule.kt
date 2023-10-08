package com.uxstate.skycast.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun
}