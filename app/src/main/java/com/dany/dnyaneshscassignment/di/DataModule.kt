package com.dany.dnyaneshscassignment.di

import com.dany.dnyaneshscassignment.data.source.ContactDataSource
import com.dany.dnyaneshscassignment.data.source.ContactDataSourceImpl
import com.dany.dnyaneshscassignment.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideContactData(apiService: ApiService): ContactDataSource {
        return ContactDataSourceImpl(apiService)
    }
}
