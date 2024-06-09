package com.bangkit.scalesappmobile.di

import com.bangkit.scalesappmobile.data.repository.UpdateScalesRepositoryImpl
import com.bangkit.scalesappmobile.domain.repository.UpdateScalesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UpdateScalesModule {

    @Binds
    abstract fun bindUpdateScalesRepository(updateScalesRepositoryImpl: UpdateScalesRepositoryImpl): UpdateScalesRepository
}