package com.bangkit.scalesappmobile.domain.usecase.scales

import com.bangkit.scalesappmobile.domain.repository.ScalesRepository
import javax.inject.Inject

class DeleteScalesUseCase @Inject constructor(
    private val scalesRepository: ScalesRepository,
) {
    suspend operator fun invoke(id: String) = scalesRepository.deleteScales(id)
}