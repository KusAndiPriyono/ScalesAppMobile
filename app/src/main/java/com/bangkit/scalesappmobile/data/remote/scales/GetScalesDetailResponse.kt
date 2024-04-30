package com.bangkit.scalesappmobile.data.remote.scales


import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.google.gson.annotations.SerializedName

data class GetScalesDetailResponse(
    @SerializedName("data")
    val data: ScalesDetails,
    @SerializedName("status")
    val status: String
)