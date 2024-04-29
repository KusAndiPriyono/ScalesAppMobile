package com.bangkit.scalesappmobile.data.remote.scales


import com.bangkit.scalesappmobile.domain.model.Scales
import com.google.gson.annotations.SerializedName

data class GetScalesDetailResponse(
    @SerializedName("data")
    val data: Scales,
    @SerializedName("status")
    val status: String
)