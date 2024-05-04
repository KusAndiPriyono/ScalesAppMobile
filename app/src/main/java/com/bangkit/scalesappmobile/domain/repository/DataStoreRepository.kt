package com.bangkit.scalesappmobile.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveOnBoarding()
    fun getOnBoarding(): Flow<Boolean>
    fun getAccessToken(): Flow<String?>
    suspend fun saveAccessToken(accessToken: String)
    suspend fun deleteAccessToken()
    fun getUserId(): Flow<String?>
    suspend fun saveUserId(userId: String)
    suspend fun clear()
}