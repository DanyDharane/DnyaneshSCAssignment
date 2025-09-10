package com.dany.dnyaneshscassignment.di

import com.dany.dnyaneshscassignment.data.source.ContactDataSource
import com.dany.dnyaneshscassignment.data.repo.ContactRepositoryImpl
import com.dany.dnyaneshscassignment.domain.repo.ContactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {
    @Provides
    fun provideContactRepository(contactDataSource: ContactDataSource
    ): ContactRepository {
        return ContactRepositoryImpl(contactDataSource)
    }
}