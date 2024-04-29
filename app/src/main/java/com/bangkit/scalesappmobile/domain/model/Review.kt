package com.bangkit.scalesappmobile.domain.model


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("review")
    val review: String,
    @SerializedName("scale")
    val scale: String,
    @SerializedName("__v")
    val v: Int
)