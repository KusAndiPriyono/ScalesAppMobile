package com.bangkit.scalesappmobile.domain.repository

import com.bangkit.scalesappmobile.data.remote.scales.PostFormKalibrasiResponse
import com.bangkit.scalesappmobile.domain.model.Form
import com.bangkit.scalesappmobile.util.Resource

interface CreateFormDocumentKalibrasiRepository {

    suspend fun createFormDocumentKalibrasi(
        formKalibrasi: Form,
    ): Resource<PostFormKalibrasiResponse>
}