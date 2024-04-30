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
fun BoxScope.EmptyStateComponent(
    anim: Int = R.raw.empty_state,
    message: String = "Nothing found here!",
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(
                Alignment.Center
            )
            .padding(16.dp)
            .testTag("Empty State Component"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnim(resId = anim)
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = message,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        content()
    }
}