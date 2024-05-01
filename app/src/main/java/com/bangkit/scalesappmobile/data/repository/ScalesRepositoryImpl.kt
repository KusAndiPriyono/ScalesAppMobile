package com.bangkit.scalesappmobile.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bangkit.scalesappmobile.data.remote.ScalesPagingSource
import com.bangkit.scalesappmobile.data.remote.ScalesApiService
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.domain.repository.ScalesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScalesRepositoryImpl @Inject constructor(
    private val scalesApiService: ScalesApiService
) : ScalesRepository {
    override fun getScales(): Flow<PagingData<Scales>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                ScalesPagingSource(scalesApiService = scalesApiService)
            }
        ).flow
    }

}