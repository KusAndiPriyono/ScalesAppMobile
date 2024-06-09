package com.bangkit.scalesappmobile.presentatiom.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.presentatiom.home.HomeNavigator
import com.bangkit.scalesappmobile.presentatiom.home.component.StandardToolbar
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun UpdateScalesScreen(
    id: String?,
    navigator: HomeNavigator,
    viewModel: UpdateViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = true, block = {
        if (id != null) {
            viewModel.updateScales(id = id)
        } else {
            navigator.popBackStack()
        }
    })

    Scaffold(
        topBar = {
            StandardToolbar(
                navigate = {
                    navigator.popBackStack()
                },
                title = {
                    Text(text = "Update Scale", fontSize = 18.sp)
                },
                showBackArrow = true,
                navActions = {}
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Nama Timbangan",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}