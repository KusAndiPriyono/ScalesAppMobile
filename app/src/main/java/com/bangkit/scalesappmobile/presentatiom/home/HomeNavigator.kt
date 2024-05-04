package com.bangkit.scalesappmobile.presentatiom.home

interface HomeNavigator {

    fun openHome()
    fun openScalesDetails(scalesId: String? = null)
    fun popBackStack()
}