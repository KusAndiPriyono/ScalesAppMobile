package com.bangkit.scalesappmobile.data.repository

import com.bangkit.scalesappmobile.data.remote.ScalesApiService
import com.bangkit.scalesappmobile.data.remote.scales.GetScalesUpdateResponse
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.domain.repository.UpdateScalesRepository
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UpdateScalesRepositoryImpl @Inject constructor(
    private val scalesApiService: ScalesApiService,
) : UpdateScalesRepository {
    override suspend fun getScalesUpdate(
        id: String,
        scalesDetails: ScalesDetails,
    ): Resource<GetScalesUpdateResponse> {
        return safeApiCall(Dispatchers.IO) {
            scalesApiService.updateScales(
                id = id,
                scalesDetails = scalesDetails
            )
        }
    }
}