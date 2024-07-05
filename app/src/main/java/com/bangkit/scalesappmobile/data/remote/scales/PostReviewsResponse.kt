package com.bangkit.scalesappmobile.data.remote.scales

import android.os.Parcelable
import com.bangkit.scalesappmobile.domain.model.Review
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostReviewsResponse(
    @SerializedName("data")
    val data: Review,
    @SerializedName("status")
    val status: String,
) : Parcelable
