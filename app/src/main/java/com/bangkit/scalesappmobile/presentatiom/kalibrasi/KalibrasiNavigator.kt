package com.bangkit.scalesappmobile.presentatiom.kalibrasi

import com.bangkit.scalesappmobile.domain.model.AllForm

interface KalibrasiNavigator {
    fun openKalibrasi()

    fun navigateBackToKalibrasi()

    fun popBackStack()

    fun openUpdateDocKalibrasi(id: String? = null, allForm: AllForm? = null)
}