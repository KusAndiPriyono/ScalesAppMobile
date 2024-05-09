package com.bangkit.scalesappmobile.presentatiom.createscales

import android.net.Uri
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun NextCreateScalesScreen(
    imageCover: Uri,
    name: String,
    brand: String,
    kindType: String,
    serialNumber: String,
    location: String,
    rangeCapacity: Int,
    unit: String,
    navigator: CreateScalesNavigator,
    viewModel: CreateScalesViewModel = hiltViewModel()
) {
    Text(text = "NextCreateScalesScreen")
}