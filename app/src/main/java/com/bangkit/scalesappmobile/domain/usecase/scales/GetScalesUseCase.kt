package com.bangkit.scalesappmobile.domain.usecase.scales

import androidx.paging.PagingData
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.domain.repository.ScalesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScalesUseCase @Inject constructor(
    private val scalesRepository: ScalesRepository,
) {
    operator fun invoke(): Flow<PagingData<Scales>> {
        return scalesRepository.getScales()
    }
}