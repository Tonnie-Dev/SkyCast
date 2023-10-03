package com.uxstate.skycast.di

import com.uxstate.skycast.data.local.converter.JsonParser
import com.uxstate.skycast.data.local.local_source.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class DataSourcesModule {

    @Binds
    abstract fun provideLocalDataSource(localDataSourceImpl: LocalDataSource): LocalDataSource

    @Binds
    abstract fun provideJsonParser(jsonParserImp:JsonParser):JsonParser
}