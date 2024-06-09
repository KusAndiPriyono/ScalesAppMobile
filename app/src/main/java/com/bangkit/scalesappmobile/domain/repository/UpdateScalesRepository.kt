package com.bangkit.scalesappmobile.domain.repository

import com.bangkit.scalesappmobile.data.remote.scales.GetScalesUpdateResponse
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.util.Resource

interface UpdateScalesRepository {
    suspend fun getScalesUpdate(
        id: String,
        scalesDetails: ScalesDetails,
    ): Resource<GetScalesUpdateResponse>
}