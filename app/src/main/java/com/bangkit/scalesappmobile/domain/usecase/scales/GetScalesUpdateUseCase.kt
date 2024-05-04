package com.bangkit.scalesappmobile.domain.usecase.scales

import com.bangkit.scalesappmobile.domain.repository.ScalesRepository
import javax.inject.Inject

class GetScalesUpdateUseCase @Inject constructor(
    private val scalesRepository: ScalesRepository
) {
    suspend operator fun invoke(token: String, id: String) =
        scalesRepository.getScalesUpdate(token, id)
}