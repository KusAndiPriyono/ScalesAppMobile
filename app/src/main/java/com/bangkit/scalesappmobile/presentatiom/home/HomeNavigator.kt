package com.bangkit.scalesappmobile.presentatiom.home

import com.bangkit.scalesappmobile.domain.model.ScalesDetails

interface HomeNavigator {

    fun openHome()
    fun openScalesDetails(id: String? = null)
    fun openUpdateScales(id: String? = null, scalesDetails: ScalesDetails? = null)
    fun popBackStack()
    fun openCreateScales()
    fun onSearchClick()
    fun openSettings()

    //    fun navigateDetails(id: String? = null)
    fun navigateBackToHome()
}