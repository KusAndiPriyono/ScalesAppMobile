package com.bangkit.scalesappmobile.domain.usecase.user

import com.bangkit.scalesappmobile.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRoleUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    operator fun invoke(): Flow<String?> = dataStoreRepository.getUserRole()
}