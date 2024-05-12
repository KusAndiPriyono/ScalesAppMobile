package com.bangkit.scalesappmobile.domain.usecase.auth

import com.bangkit.scalesappmobile.domain.repository.AuthRepository
import javax.inject.Inject

class SaveAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(token: String) = authRepository.saveAccessToken(token)
}
