package com.bangkit.scalesappmobile.data.remote.scales

import android.os.Parcelable
import com.bangkit.scalesappmobile.domain.model.Location
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationsResponse(
    @SerializedName("data")
    val data: List<Location>,
    @SerializedName("status")
    val status: String,
) : Parcelable
