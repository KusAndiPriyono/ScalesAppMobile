package com.bangkit.scalesappmobile.presentatiom.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun SearchScreen(
    navigator: SearchNavigator,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    Text(text = "Search Screen")
}