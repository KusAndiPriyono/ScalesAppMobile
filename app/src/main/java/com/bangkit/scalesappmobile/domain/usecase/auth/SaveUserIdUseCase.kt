package com.bangkit.scalesappmobile.domain.usecase.auth

import com.bangkit.scalesappmobile.domain.repository.AuthRepository
import javax.inject.Inject

class SaveUserIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(userId: String) = authRepository.saveUserId(userId)
}
