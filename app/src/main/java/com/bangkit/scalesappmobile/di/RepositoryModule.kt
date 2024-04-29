package com.bangkit.scalesappmobile.di

import com.bangkit.scalesappmobile.data.remote.scales.ScalesApiService
import com.bangkit.scalesappmobile.data.repository.ScalesRepositoryImpl
import com.bangkit.scalesappmobile.domain.repository.ScalesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideScalesRepository(scalesApiService: ScalesApiService): ScalesRepository {
        return ScalesRepositoryImpl(scalesApiService)
    }
}