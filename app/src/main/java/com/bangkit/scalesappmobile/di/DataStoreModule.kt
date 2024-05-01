package com.bangkit.scalesappmobile.di

import android.app.Application
import com.bangkit.scalesappmobile.data.repository.DataStoreRepositoryImpl
import com.bangkit.scalesappmobile.domain.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

//    @Binds
//    @Singleton
//    abstract fun bindDataStoreRepository(dataStoreRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository

    @Provides
    @Singleton
    fun provideDataStoreRepository(application: Application): DataStoreRepository {
        return DataStoreRepositoryImpl(application)
    }

}