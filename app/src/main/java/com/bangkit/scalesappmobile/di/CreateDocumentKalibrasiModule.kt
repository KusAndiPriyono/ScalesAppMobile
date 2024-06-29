package com.bangkit.scalesappmobile.di

import com.bangkit.scalesappmobile.data.repository.CreateFormDocumentKalibrasiRepositoryImpl
import com.bangkit.scalesappmobile.domain.repository.CreateFormDocumentKalibrasiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CreateDocumentKalibrasiModule {

    @Binds
    abstract fun bindCreateFormDocumentKalibrasiRepository(createFormDocumentKalibrasiImpl: CreateFormDocumentKalibrasiRepositoryImpl): CreateFormDocumentKalibrasiRepository
}