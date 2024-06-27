package com.bangkit.scalesappmobile.domain.repository

import com.bangkit.scalesappmobile.data.remote.scales.UpdateDocumentKalibrasiResponse
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.util.Resource

interface DocumentKalibrasiRepository {

    suspend fun getAllDocumentKalibrasi(): Resource<List<AllForm>>

    suspend fun updateDocumentKalibrasiDetail(id: String, allForm: AllForm): Resource<UpdateDocumentKalibrasiResponse>

    suspend fun deleteDocumentKalibrasi(id: String): Resource<Boolean>
}