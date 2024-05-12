package com.bangkit.scalesappmobile.domain.repository

import androidx.paging.PagingData
import com.bangkit.scalesappmobile.domain.model.Location
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.util.Resource
import kotlinx.coroutines.flow.Flow

interface ScalesRepository {

    fun getScales(location: String?): Flow<PagingData<Scales>>

    suspend fun getScalesDetail(id: String): Resource<ScalesDetails>

    suspend fun getScalesUpdate(token: String, id: String): Resource<ScalesDetails>

    suspend fun getScalesLocations(): Resource<List<Location>>
}