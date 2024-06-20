package com.bangkit.scalesappmobile.data.remote.scales

import android.os.Parcelable
import com.bangkit.scalesappmobile.domain.model.Form
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostFormKalibrasiResponse(
    @SerializedName("data")
    val data: Form,
    @SerializedName("status")
    val status: String,
) : Parcelable
