package com.bangkit.scalesappmobile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ScalesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }
}

private fun setupTimber() {
    Timber.plant(Timber.DebugTree())
}