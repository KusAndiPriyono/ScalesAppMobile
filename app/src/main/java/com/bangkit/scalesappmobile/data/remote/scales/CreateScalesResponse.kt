package com.bangkit.scalesappmobile.data.remote.scales


import com.bangkit.scalesappmobile.domain.model.CreateScalesRequest
import com.google.gson.annotations.SerializedName


data class CreateScalesResponse(
    @SerializedName("data")
    val data: CreateScalesRequest,
    @SerializedName("status")
    val status: String,
)