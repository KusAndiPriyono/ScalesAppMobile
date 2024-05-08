package com.bangkit.scalesappmobile.data.remote.scales


import android.os.Parcelable
import com.bangkit.scalesappmobile.domain.model.CreateScalesRequest
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateScalesResponse(
    @SerializedName("data")
    val data: CreateScalesRequest,
    @SerializedName("status")
    val status: String,
) : Parcelable