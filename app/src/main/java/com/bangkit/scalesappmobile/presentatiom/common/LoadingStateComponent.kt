package com.bangkit.scalesappmobile.presentatiom.common

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.R

@Composable
fun BoxScope.LoadingStateComponent() {
    LottieAnim(
        resId = R.raw.loading, modifier = Modifier
            .size(130.dp)
            .align(Alignment.Center)
            .testTag("loading Component")
    )
}

@Composable
fun BoxScope.LoadingStateButtonCreateComponent() {
    LottieAnim(
        resId = R.raw.loading, modifier = Modifier
            .size(75.dp)
            .fillMaxWidth()
            .align(Alignment.Center)
            .testTag("loading Component")
    )
}