package com.bangkit.scalesappmobile.data.remote.scales

import com.bangkit.scalesappmobile.domain.model.AllForm
import com.google.gson.annotations.SerializedName

data class GetAllFormsResponse(
    @SerializedName("data")
    val data: List<AllForm>,
    @SerializedName("results")
    val results: Int,
    @SerializedName("status")
    val status: String,
)
