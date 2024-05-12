package com.bangkit.scalesappmobile.domain.usecase.auth

import com.bangkit.scalesappmobile.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String
    ) = authRepository.registerUser(
        name = name,
        email = email,
        password = password,
        passwordConfirm = passwordConfirm
    )
}