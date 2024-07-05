package com.bangkit.scalesappmobile.data.remote.scales

import android.os.Parcelable
import com.bangkit.scalesappmobile.domain.model.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetUserMeResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("user")
    val user: User,
) : Parcelable
