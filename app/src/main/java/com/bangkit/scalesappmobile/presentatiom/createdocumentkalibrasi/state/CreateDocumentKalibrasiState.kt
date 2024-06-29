package com.bangkit.scalesappmobile.presentatiom.createdocumentkalibrasi.state

import com.bangkit.scalesappmobile.data.remote.scales.PostFormKalibrasiResponse

data class CreateDocumentKalibrasiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val createFormDocumentKalibrasi: PostFormKalibrasiResponse? = null,
)
