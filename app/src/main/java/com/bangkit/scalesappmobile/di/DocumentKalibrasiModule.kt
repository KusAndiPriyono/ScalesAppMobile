package com.bangkit.scalesappmobile.di

import com.bangkit.scalesappmobile.data.repository.DocumentKalibrasiRepositoryImpl
import com.bangkit.scalesappmobile.domain.repository.DocumentKalibrasiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DocumentKalibrasiModule {

    @Binds
    abstract fun bindDocumentKalibrasiRepository(
        documentKalibrasiRepositoryImpl: DocumentKalibrasiRepositoryImpl,
    ): DocumentKalibrasiRepository
}