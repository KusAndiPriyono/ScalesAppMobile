package com.bangkit.scalesappmobile.domain.usecase.scales

import com.bangkit.scalesappmobile.domain.repository.ScalesRepository
import javax.inject.Inject

class GetScalesUseCase @Inject constructor(
    private val scalesRepository: ScalesRepository,
) {
//    operator fun invoke(location: String?): Flow<PagingData<Scales>> {
//        return scalesRepository.getScales(location)
//    }

    suspend operator fun invoke(location: String?) = scalesRepository.getScales(location)
}