package com.bangkit.scalesappmobile.domain.repository

import com.bangkit.scalesappmobile.domain.model.Location
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.util.Resource

interface ScalesRepository {

    //    fun getScales(location: String?): Flow<PagingData<Scales>>
    suspend fun getScales(location: String?): Resource<List<Scales>>

    suspend fun getScalesDetail(id: String): Resource<ScalesDetails>

    suspend fun getScalesUpdate(token: String, id: String): Resource<ScalesDetails>

    suspend fun getScalesLocations(): Resource<List<Location>>
}