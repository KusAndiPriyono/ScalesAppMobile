package com.bangkit.scalesappmobile.domain.usecase.onboarding

import com.bangkit.scalesappmobile.domain.repository.DataStoreRepository
import javax.inject.Inject

class GetIfUserIsLoggedInUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() = dataStoreRepository.getUserId()
}