package com.bangkit.scalesappmobile.di

import com.bangkit.scalesappmobile.data.repository.CreateScalesRepositoryImpl
import com.bangkit.scalesappmobile.domain.repository.CreateScalesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CreateScalesModule {

    @Binds
    abstract fun bindCreateScalesRepository(createScalesRepositoryImpl: CreateScalesRepositoryImpl): CreateScalesRepository
}