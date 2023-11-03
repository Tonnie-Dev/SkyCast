package com.uxstate.skycast.di

import com.uxstate.skycast.domain.connectivity.ConnectivityObserver
import com.uxstate.skycast.domain.connectivity.ConnectivityObserverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityObserver(connectivityObserverImpl: ConnectivityObserverImpl): ConnectivityObserver
}