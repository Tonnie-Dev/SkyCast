package com.uxstate.skycast.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import com.uxstate.skycast.domain.connectivity.ConnectivityObserver
import com.uxstate.skycast.domain.connectivity.ConnectivityObserverImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityObserver(connectivityObserverImpl: ConnectivityObserverImpl): ConnectivityObserver

    companion object {

  @Provides
        @Singleton
        fun provideConnectivityManager(@ApplicationContext context: Context):ConnectivityManager

    = context. getSystemService(ConnectivityManager::class.java)





    }
}