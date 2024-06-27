package com.bangkit.scalesappmobile.presentatiom.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.presentatiom.home.component.StandardToolbar
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.KalibrasiNavigator
import com.bangkit.scalesappmobile.util.UiEvents
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun UpdateDocKalibrasiScreen(
    id: String?,
    allForm: AllForm?,
    navigator: KalibrasiNavigator,
    viewModel: UpdateDocKalibrasiViewModel = hiltViewModel()
) {
    val updateDocKalibrasiState = viewModel.updateDocState.value
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(key1 = true, block = {
        if (id != null && allForm != null) {
            viewModel.loadDocumentKalibrasi(allForm)
        } else {
            navigator.popBackStack()
        }
    })

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }

                is UiEvents.NavigationEvent -> {
                    navigator.navigateBackToKalibrasi()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            StandardToolbar(
                navigate = {
                    navigator.popBackStack()
                },
                title = {
                    Text(text = "Edit Document Kalibrasi", fontSize = 18.sp)
                },
                showBackArrow = true,
                navActions = {

                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {}
    }
}