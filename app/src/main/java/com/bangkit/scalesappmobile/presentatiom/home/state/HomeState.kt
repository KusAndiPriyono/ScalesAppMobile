package com.bangkit.scalesappmobile.presentatiom.home.state

import com.bangkit.scalesappmobile.domain.model.Scales

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val scrollValue: Int = 0,
    val maxScrollingValue: Int = 0,
    val scales: List<Scales> = emptyList(),
)