package com.bangkit.scalesappmobile.domain.repository

import com.bangkit.scalesappmobile.domain.model.AuthResponse
import com.bangkit.scalesappmobile.util.Resource

interface AuthRepository {

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Resource<AuthResponse>

    suspend fun loginUser(email: String, password: String): Resource<AuthResponse>
    suspend fun forgotPassword(email: String): Resource<Any>
    suspend fun logoutUser()
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveUserId(userId: String)
}