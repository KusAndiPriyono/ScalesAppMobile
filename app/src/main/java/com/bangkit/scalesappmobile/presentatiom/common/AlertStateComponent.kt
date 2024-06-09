package com.bangkit.scalesappmobile.presentatiom.common

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.R

@Composable
fun AlertStateComponent() {
    LottieAnim(resId = R.raw.alert_dialog, modifier = Modifier.size(60.dp))
}