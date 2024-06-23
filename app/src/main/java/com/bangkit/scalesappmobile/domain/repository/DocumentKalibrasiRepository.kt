package com.bangkit.scalesappmobile.domain.repository

import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.util.Resource

interface DocumentKalibrasiRepository {

    suspend fun getAllDocumentKalibrasi(): Resource<List<AllForm>>

    suspend fun getDocumentKalibrasiDetail(id: String): Resource<AllForm>
}