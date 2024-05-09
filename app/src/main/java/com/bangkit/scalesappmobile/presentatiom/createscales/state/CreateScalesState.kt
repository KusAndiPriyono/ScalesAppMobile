package com.bangkit.scalesappmobile.presentatiom.createscales.state

import com.bangkit.scalesappmobile.data.remote.scales.CreateScalesResponse

data class CreateScalesState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val scalesIsSaved: CreateScalesResponse? = null,
)