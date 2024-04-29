package com.bangkit.scalesappmobile.domain.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    val email: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("__v")
    val v: Int
)