package com.bangkit.scalesappmobile.domain.usecase.onboarding

import com.bangkit.scalesappmobile.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveOnBoarding @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    suspend operator fun invoke() {
        dataStoreRepository.saveOnBoarding()
    }
}