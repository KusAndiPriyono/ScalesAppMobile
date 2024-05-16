package com.bangkit.scalesappmobile.domain.usecase.scales

import androidx.paging.PagingData
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.domain.repository.ScalesRepository
import com.bangkit.scalesappmobile.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchScalesUseCase @Inject constructor(
    private val scalesRepository: ScalesRepository,
) {
    suspend operator fun invoke(brand: List<String>): Resource<Flow<PagingData<Scales>>> =
        scalesRepository.searchScales(brand)
}