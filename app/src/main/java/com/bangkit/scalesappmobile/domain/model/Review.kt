package com.bangkit.scalesappmobile.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Review(
    @SerializedName("createdAt")
    val createdAt: Date,
//    @SerializedName("_id")
//    val id: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("review")
    val review: String,
    @SerializedName("scale")
    val scale: String,
//    @SerializedName("user")
//    val user: User,
) : Parcelable