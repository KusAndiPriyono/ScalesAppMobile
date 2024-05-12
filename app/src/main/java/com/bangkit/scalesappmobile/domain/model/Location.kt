package com.bangkit.scalesappmobile.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Location(
    @SerializedName("id")
    val id: String,
    @SerializedName("location")
    val location: String,
) : Parcelable