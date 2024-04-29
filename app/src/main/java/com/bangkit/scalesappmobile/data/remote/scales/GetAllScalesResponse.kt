package com.bangkit.scalesappmobile.data.remote.scales


import com.bangkit.scalesappmobile.domain.model.Scales
import com.google.gson.annotations.SerializedName

data class GetAllScalesResponse(
    @SerializedName("data")
    val data: List<Scales>,
    @SerializedName("results")
    val results: Int,
    @SerializedName("status")
    val status: String
)