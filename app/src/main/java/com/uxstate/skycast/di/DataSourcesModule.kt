package com.uxstate.skycast.di

import com.uxstate.skycast.data.local.converter.JsonParser
import com.uxstate.skycast.data.local.converter.JsonParserImpl
import com.uxstate.skycast.data.local.local_source.LocalDataSource
import com.uxstate.skycast.data.local.local_source.LocalDataSourceImpl
import com.uxstate.skycast.data.remote.remote_source.RemoteDataSource
import com.uxstate.skycast.data.remote.remote_source.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class DataSourcesModule {

    @Binds
    abstract fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl):RemoteDataSource

    @Binds
    abstract fun provideJsonParser(jsonParserImp:JsonParserImpl):JsonParser


}