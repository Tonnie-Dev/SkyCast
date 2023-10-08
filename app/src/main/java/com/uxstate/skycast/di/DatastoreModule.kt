package com.uxstate.skycast.di

import com.uxstate.skycast.data.prefs.DataStoreOperationsImpl
import com.uxstate.skycast.domain.prefs.DataStoreOperations
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class DatastoreModule {

    @Binds

   abstract fun bindDataStoreOperations(dataStoreOperationsImpl: DataStoreOperationsImpl): DataStoreOperations
}