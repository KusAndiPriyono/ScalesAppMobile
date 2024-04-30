package com.bangkit.scalesappmobile.presentatiom.common

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.R

@Composable
fun BoxScope.ErrorStateComponent(errorMessage: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .align(Alignment.Center)
            .padding(16.dp)
            .testTag("Error State Component")
    ) {
        LottieAnim(resId = R.raw.error_anim)
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = errorMessage,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}