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
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.bangkit.scalesappmobile.MainActivity
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.data.remote.ScalesApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.net.UnknownHostException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    private val scalesApiService: ScalesApiService,
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        return try {
            val response = scalesApiService.getPost()
            if (response.status == "success") {
                // Log success and details
                Timber.tag("NotificationWorker").d("Success!")
                response.data.let { data ->
                    val now = ZonedDateTime.now()
                    data.forEach { scale ->
                        val nextCalibrationDate = ZonedDateTime.parse(
                            scale.nextCalibrationDate,
                            DateTimeFormatter.ISO_ZONED_DATE_TIME
                        )
                        val daysUntilNextCalibration =
                            ChronoUnit.DAYS.between(now, nextCalibrationDate)

                        if (daysUntilNextCalibration in 1..30) {
                            Timber.tag("NotificationWorker")
                                .d(
                                    "Id: ${scale.id}, Name: ${scale.name}, Location: ${scale.location}",
                                    "Next Calibration Date: ${scale.nextCalibrationDate}"
                                )
                            showNotification(
                                scale.id.hashCode(),
                                scale.name,
                                scale.location,
                                scale.nextCalibrationDate
                            )
                        }
                    }
                }
                // Show notification for each scale

                Result.success()
            } else {
                Timber.tag("NotificationWorker").d("Retrying...")
                Result.retry()
            }
        } catch (e: Exception) {
            if (e is UnknownHostException) {
                Timber.tag("NotificationWorker").e("Retrying...")
                Result.retry()
            } else {
                Timber.tag("NotificationWorker").e("Error: %s", e.message)
                Result.failure(Data.Builder().putString("error", e.message).build())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(
        id: Int,
        name: String,
        lokasi: String,
        nextCalibrationDate: String,
    ) {
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
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(
                "Kalibrasi untuk $name di $lokasi akan dilakukan pada " +
                        formatDate(nextCalibrationDate) + ". " +
                        "Jangan lupa untuk melakukan kalibrasi!"
            )

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Calibration Reminder")
            .setContentText(
                "Kalibrasi untuk $name di $lokasi akan dilakukan pada " +
                        formatDate(nextCalibrationDate) + ".\n" +
                        "Jangan lupa untuk melakukan kalibrasi!"
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setStyle(bigTextStyle)
            .build()

        notificationManager.notify(id, notification)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_ZONED_DATE_TIME)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        "Invalid date"
    }
}
