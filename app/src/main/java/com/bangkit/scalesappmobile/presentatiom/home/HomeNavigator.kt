package com.bangkit.scalesappmobile.presentatiom.home

import com.bangkit.scalesappmobile.domain.model.ScalesDetails

interface HomeNavigator {

    fun openHome()
    fun openScalesDetails(id: String? = null)
    fun openUpdateScales(id: String? = null, scalesDetails: ScalesDetails? = null)
    fun popBackStack()
    fun openCreateScales()
    fun onSearchClick()

    fun openKalibrasi()

    fun openSchedule()

    fun openNotifications()
    fun openSettings()
    fun navigateBackToHome()

    fun openCreateDocumentKalibrasi(id: String? = null)
}