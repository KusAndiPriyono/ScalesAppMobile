package com.bangkit.scalesappmobile.presentatiom.createscales

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun CreateScalesScreen(
    navigator: CreateScalesNavigator,
) {
    Text(text = "Create Scales Screen", modifier = Modifier.fillMaxSize())
}