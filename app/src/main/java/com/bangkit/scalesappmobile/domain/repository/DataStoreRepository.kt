package com.bangkit.scalesappmobile.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveOnBoarding()
    fun getOnBoarding(): Flow<Boolean>

}