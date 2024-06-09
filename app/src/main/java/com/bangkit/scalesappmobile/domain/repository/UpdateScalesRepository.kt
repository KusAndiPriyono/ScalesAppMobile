package com.bangkit.scalesappmobile.domain.repository

import com.bangkit.scalesappmobile.data.remote.scales.GetScalesUpdateResponse
import com.bangkit.scalesappmobile.domain.model.UpdateRequest
import com.bangkit.scalesappmobile.util.Resource

interface UpdateScalesRepository {
    suspend fun getScalesUpdate(
        id: String,
        updateRequest: UpdateRequest
    ): Resource<GetScalesUpdateResponse>
}