package com.bangkit.scalesappmobile.domain.usecase.notification

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.bangkit.scalesappmobile.data.remote.ScalesApiService
import com.bangkit.scalesappmobile.presentatiom.notifications.NotificationWorker
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(private val scalesApiService: ScalesApiService) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker = NotificationWorker(scalesApiService, appContext, workerParameters)
}