package com.bangkit.scalesappmobile.presentatiom.home

interface HomeNavigator {

    fun openHome()
    fun openScalesDetails(id: String? = null)
    fun popBackStack()
    fun openCreateScales()
    fun onSearchClick()
    fun openSettings()
}