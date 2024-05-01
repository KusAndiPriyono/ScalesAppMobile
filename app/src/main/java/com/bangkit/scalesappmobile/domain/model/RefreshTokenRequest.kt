package com.bangkit.scalesappmobile.domain.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("token")
    val token: String,
)
