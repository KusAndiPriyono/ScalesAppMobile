package com.bangkit.scalesappmobile.data.repository

import com.bangkit.scalesappmobile.data.remote.ScalesApiService
import com.bangkit.scalesappmobile.data.remote.scales.AuthResponse
import com.bangkit.scalesappmobile.domain.model.ForgotPasswordRequest
import com.bangkit.scalesappmobile.domain.model.LoginRequest
import com.bangkit.scalesappmobile.domain.model.RegisterRequest
import com.bangkit.scalesappmobile.domain.repository.AuthRepository
import com.bangkit.scalesappmobile.domain.repository.DataStoreRepository
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val scalesApiService: ScalesApiService,
    private val dataStoreRepository: DataStoreRepository,
) : AuthRepository {
    override suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String,
    ): Resource<AuthResponse> {
        return safeApiCall(Dispatchers.IO) {
            scalesApiService.registerUser(
                RegisterRequest(
                    name = name,
                    email = email,
                    password = password,
                    passwordConfirm = passwordConfirm
                )
            )
        }
    }

    override suspend fun loginUser(email: String, password: String): Resource<AuthResponse> {
        return safeApiCall(Dispatchers.IO) {
            scalesApiService.loginUser(
                LoginRequest(
                    email = email,
                    password = password
                )
            )
        }
    }

    override suspend fun forgotPassword(email: String): Resource<ForgotPasswordRequest> {
        return safeApiCall(Dispatchers.IO) {
            scalesApiService.forgotPassword(ForgotPasswordRequest(email))
        }
    }

    override suspend fun logoutUser() {
        dataStoreRepository.clear()
    }

    override suspend fun saveAccessToken(token: String) {
        dataStoreRepository.saveAccessToken(token)
    }

    override suspend fun saveUserId(id: String) {
        dataStoreRepository.saveUserId(id)
    }
}