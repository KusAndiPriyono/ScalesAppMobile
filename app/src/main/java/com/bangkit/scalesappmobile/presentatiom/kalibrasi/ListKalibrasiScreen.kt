package com.bangkit.scalesappmobile.presentatiom.kalibrasi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ListKalibrasiScreen(
    navigator: KalibrasiNavigator,
) {
    ListKalibrasiScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListKalibrasiScreenContent(

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Daftar List Form Kalibrasi", fontSize = 18.sp)
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val scrollState = rememberScrollState()
            LaunchedEffect(key1 = Unit) {
                scrollState.animateScrollTo(100)
            }
        }
    }
}