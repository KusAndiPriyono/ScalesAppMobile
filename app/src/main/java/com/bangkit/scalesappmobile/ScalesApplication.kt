package com.bangkit.scalesappmobile

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.bangkit.scalesappmobile.domain.usecase.notification.CustomWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class ScalesApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CustomWorkerFactory

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()
}

private fun setupTimber() {
    Timber.plant(Timber.DebugTree())
}