package com.bangkit.scalesappmobile.domain.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("error")
    val error: List<String>? = null,
    @field:SerializedName("status")
    val status: String? = null
)
