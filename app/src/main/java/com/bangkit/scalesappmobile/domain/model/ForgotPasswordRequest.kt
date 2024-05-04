package com.bangkit.scalesappmobile.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForgotPasswordRequest(
    @SerializedName("email")
    val email: String,
) : Parcelable