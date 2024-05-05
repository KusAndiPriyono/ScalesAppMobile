package com.bangkit.scalesappmobile.util

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val SCALES_DATA_STORE = "scalesDataStore"
    const val BASE_URL = "https://scalesapp.up.railway.app/"

    val USER_ID = stringPreferencesKey("id")
    val TOKEN = stringPreferencesKey("token")
}