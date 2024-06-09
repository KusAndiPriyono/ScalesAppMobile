package com.bangkit.scalesappmobile.domain.usecase.scales

import com.bangkit.scalesappmobile.domain.repository.UpdateScalesRepository
import javax.inject.Inject

class GetScalesUpdateUseCase @Inject constructor(
    private val updateScalesRepository: UpdateScalesRepository
) {
//    suspend operator fun invoke(id: String, updateRequest: UpdateRequest) =
//        updateScalesRepository(id, updateRequest)
}