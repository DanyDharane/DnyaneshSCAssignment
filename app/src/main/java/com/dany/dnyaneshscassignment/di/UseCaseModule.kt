package com.dany.dnyaneshscassignment.di

import com.dany.dnyaneshscassignment.domain.usecase.GetContactsUseCase
import com.dany.dnyaneshscassignment.domain.repo.ContactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideCatUseCase(contactRepository: ContactRepository): GetContactsUseCase {
        return GetContactsUseCase(contactRepository)
    }
}
