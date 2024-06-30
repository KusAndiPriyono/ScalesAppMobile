package com.bangkit.scalesappmobile.presentatiom.notifications

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.bangkit.scalesappmobile.domain.usecase.scales.GetScalesDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getScalesDetailUseCase: GetScalesDetailUseCase,
    private val application: Application,
) : ViewModel() {

    // Example function to schedule notification for a specific scales ID
    fun scheduleNotificationWorker(id: String) {
        viewModelScope.launch {
            try {
                // Fetch scales details using the use case
                val scalesDetail = getScalesDetailUseCase(id)

                // Prepare input data for NotificationWorker
                val inputData = workDataOf(
                    "nextCalibrationDate" to (scalesDetail.data?.nextCalibrationDate ?: ""),
                    "name" to (scalesDetail.data?.name ?: ""),
                    "lokasi" to (scalesDetail.data?.location ?: ""),
                    "id" to scalesDetail.data?.id.toString()
                )

                // Schedule WorkManager to execute NotificationWorker
                val notificationWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                    .setInputData(inputData)
                    .build()

                val workManager = WorkManager.getInstance(application)
                workManager.enqueue(notificationWorkRequest)

            } catch (e: Exception) {
                // Handle exceptions or errors
                Timber.tag("NotificationViewModel").e(e, "Error scheduling notification")
            }
        }
    }
}
