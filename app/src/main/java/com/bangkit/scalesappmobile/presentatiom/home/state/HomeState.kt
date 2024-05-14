package com.bangkit.scalesappmobile.presentatiom.home.state

data class HomeState(
    val isLoading: Boolean = false,
    val scrollValue: Int = 0,
    val maxScrollingValue: Int = 0,
)