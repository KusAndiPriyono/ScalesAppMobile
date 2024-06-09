package com.bangkit.scalesappmobile.data.remote.scales


import android.os.Parcelable
import com.bangkit.scalesappmobile.domain.model.UpdateRequest
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetScalesUpdateResponse(
    @SerializedName("data")
    val data: UpdateRequest,
    @SerializedName("status")
    val status: String
) : Parcelable