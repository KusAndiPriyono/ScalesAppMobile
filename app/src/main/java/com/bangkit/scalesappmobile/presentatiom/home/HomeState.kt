package com.bangkit.scalesappmobile.presentatiom.home

import com.bangkit.scalesappmobile.data.remote.scales.AuthResponse

data class HomeState(
    val isLoading: Boolean = false,
    val scrollValue: Int = 0,
    val maxScrollingValue: Int = 0,
    val data: AuthResponse? = null,
)