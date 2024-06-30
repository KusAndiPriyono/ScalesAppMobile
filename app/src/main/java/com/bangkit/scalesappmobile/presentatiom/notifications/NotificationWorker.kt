package com.bangkit.scalesappmobile.presentatiom.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bangkit.scalesappmobile.MainActivity
import com.bangkit.scalesappmobile.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
) : Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        val nextCalibrationDate =
            inputData.getString("nextCalibrationDate") ?: return Result.failure()
        val name = inputData.getString("name") ?: "Unknown Scales"
        val lokasi = inputData.getString("lokasi") ?: "Unknown Location"
        val id = inputData.getString("id")?.toIntOrNull() ?: return Result.failure()

        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        val calibrationDate = LocalDate.parse(nextCalibrationDate, formatter)
        val currentDate = LocalDate.now()

        if (currentDate.isEqual(calibrationDate)) {
            showNotification(id, name, lokasi)
        }
        return Result.success()
    }

    private fun showNotification(id: Int, name: String, lokasi: String) {
        val channelId = "CALIBRATION_CHANNEL"
        val channelName = "Calibration Notification"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for Calibration Reminder"
            }
            notificationManager.createNotificationChannel(channel)
        }


        val notificationIntent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("id", id)
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            id,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Calibration Reminder")
            .setContentText("The calibration for $name at $lokasi is due today.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        notificationManager.notify(id, notification)
    }
}