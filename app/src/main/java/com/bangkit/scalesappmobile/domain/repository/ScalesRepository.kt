package com.bangkit.scalesappmobile.domain.repository

import androidx.paging.PagingData
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.util.Resource
import kotlinx.coroutines.flow.Flow

interface ScalesRepository {

    fun getScales(): Flow<PagingData<Scales>>
    suspend fun searchScales(slug: List<String>): Resource<Flow<PagingData<Scales>>>
    suspend fun getScalesDetail(id: String): Resource<ScalesDetails>
    suspend fun deleteScales(id: String): Resource<Boolean>
}