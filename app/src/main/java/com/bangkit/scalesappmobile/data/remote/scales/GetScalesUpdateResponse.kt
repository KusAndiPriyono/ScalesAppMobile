package com.bangkit.scalesappmobile.data.remote.scales


import android.os.Parcelable
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetScalesUpdateResponse(
    @SerializedName("data")
    val data: ScalesDetails,
    @SerializedName("status")
    val status: String,
) : Parcelable