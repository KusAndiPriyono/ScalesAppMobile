package com.bangkit.scalesappmobile.data.repository

import com.bangkit.scalesappmobile.data.remote.ScalesApiService
import com.bangkit.scalesappmobile.data.remote.scales.PostFormKalibrasiResponse
import com.bangkit.scalesappmobile.domain.model.Form
import com.bangkit.scalesappmobile.domain.repository.CreateFormDocumentKalibrasiRepository
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CreateFormDocumentKalibrasiRepositoryImpl @Inject constructor(
    private val scalesApiService: ScalesApiService,
) : CreateFormDocumentKalibrasiRepository {
    override suspend fun createFormDocumentKalibrasi(formKalibrasi: Form): Resource<PostFormKalibrasiResponse> {
        return safeApiCall(Dispatchers.IO) {
            scalesApiService.createFormKalibrasi(
                formKalibrasi = formKalibrasi
            )
        }
    }
}