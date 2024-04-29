package com.bangkit.scalesappmobile.domain.usecase.onboarding

import com.bangkit.scalesappmobile.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnBoarding @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    operator fun invoke(): Flow<Boolean?> {
        return dataStoreRepository.getOnBoarding()
    }
}