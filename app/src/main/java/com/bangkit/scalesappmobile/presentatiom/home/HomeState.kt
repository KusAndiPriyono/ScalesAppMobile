package com.bangkit.scalesappmobile.presentatiom.home

data class HomeState(
    val isLoading: Boolean = false,
    val scrollValue: Int = 0,
    val maxScrollingValue: Int = 0
)