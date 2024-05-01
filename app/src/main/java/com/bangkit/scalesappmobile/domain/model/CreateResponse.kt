package com.bangkit.scalesappmobile.domain.model


import com.google.gson.annotations.SerializedName

data class CreateResponse(
    @SerializedName("data")
    val data: Scales,
    @SerializedName("status")
    val status: String
)