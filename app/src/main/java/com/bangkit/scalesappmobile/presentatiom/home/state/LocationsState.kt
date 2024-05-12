package com.bangkit.scalesappmobile.presentatiom.home.state

import com.bangkit.scalesappmobile.domain.model.Location

data class LocationsState(
    val isLoading: Boolean = false,
//    val scrollValue: Int = 0,
//    val maxScrollingValue: Int = 0,
    val error: String? = null,
    val locations: List<Location> = emptyList(),
)
