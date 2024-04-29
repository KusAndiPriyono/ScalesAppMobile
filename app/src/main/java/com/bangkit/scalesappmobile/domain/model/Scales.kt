package com.bangkit.scalesappmobile.domain.model


import com.google.gson.annotations.SerializedName

data class Scales(
    @SerializedName("_id")
    val id: String,
    @SerializedName("imageCover")
    val imageCover: String,
    @SerializedName("name")
    val name: String,
)