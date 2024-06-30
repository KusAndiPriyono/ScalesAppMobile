package com.bangkit.scalesappmobile.presentatiom.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun NotificationScreen(
    id: String?,
    viewModel: NotificationViewModel = hiltViewModel(),
) {
    // Example: Observe data from ViewModel
    LaunchedEffect(key1 = true) {
        id?.let { viewModel.scheduleNotificationWorker(it) }
    }

    // Placeholder content for the notification screen
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Notification Screen")
    }
}