package com.bangkit.scalesappmobile.data.remote.scales


import android.os.Parcelable
import com.bangkit.scalesappmobile.domain.model.AllReviews
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetAllReviewsResponse(
    @SerializedName("data")
    val data: List<AllReviews>,
    @SerializedName("results")
    val results: Int,
    @SerializedName("status")
    val status: String,
) : Parcelable