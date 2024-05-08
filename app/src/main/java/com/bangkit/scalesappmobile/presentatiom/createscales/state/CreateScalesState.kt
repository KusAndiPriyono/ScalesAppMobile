package com.bangkit.scalesappmobile.presentatiom.createscales.state

import com.bangkit.scalesappmobile.data.remote.scales.ErrorResponse

data class CreateScalesState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val scalesIsSaved: ErrorResponse = ErrorResponse(),
)