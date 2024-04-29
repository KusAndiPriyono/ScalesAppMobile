package com.bangkit.scalesappmobile.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveOnBoarding()
    fun getOnBoarding(): Flow<Boolean?>
    suspend fun saveLogin()
    suspend fun logout()
    fun getLogin(): Flow<Boolean?>
    fun getToken(): Flow<String>
    suspend fun saveToken(token: String)

}