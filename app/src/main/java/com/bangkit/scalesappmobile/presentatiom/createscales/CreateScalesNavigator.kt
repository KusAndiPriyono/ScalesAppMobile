package com.bangkit.scalesappmobile.presentatiom.createscales

import android.net.Uri

interface CreateScalesNavigator {

    fun openNextCreateScalesScreen(
        imageCover: Uri,
        name: String,
        brand: String,
        kindType: String,
        serialNumber: String,
        location: String,
        rangeCapacity: Int,
        unit: String,
    )

    fun popBackStack()
    fun navigateBackToHome()
}
