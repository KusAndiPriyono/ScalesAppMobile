package com.bangkit.scalesappmobile.data.remote.scales


import android.os.Parcelable
import com.bangkit.scalesappmobile.domain.model.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: User
) : Parcelable