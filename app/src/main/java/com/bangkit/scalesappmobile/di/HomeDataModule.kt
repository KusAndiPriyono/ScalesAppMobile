package com.bangkit.scalesappmobile.di

import com.bangkit.scalesappmobile.data.repository.ScalesRepositoryImpl
import com.bangkit.scalesappmobile.domain.repository.ScalesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeDataModule {

    @Binds
    abstract fun bindScalesRepository(
        scalesRepositoryImpl: ScalesRepositoryImpl
    ): ScalesRepository
}