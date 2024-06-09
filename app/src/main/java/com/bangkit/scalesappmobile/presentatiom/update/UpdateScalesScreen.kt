package com.bangkit.scalesappmobile.presentatiom.update

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.presentatiom.home.HomeNavigator
import com.bangkit.scalesappmobile.presentatiom.home.component.StandardToolbar
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.bangkit.scalesappmobile.util.UiEvents
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun UpdateScalesScreen(
    id: String?,
    scalesDetails: ScalesDetails?,
    navigator: HomeNavigator,
    viewModel: UpdateViewModel = hiltViewModel(),
) {
    val updateScalesState = viewModel.updateScales.value
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(key1 = true, block = {
        if (id != null && scalesDetails != null) {
            viewModel.loadScalesData(scalesDetails)
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
                    navigator.openScalesDetails(id)
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
                    Text(text = "Edit Scales", fontSize = 18.sp)
                },
                showBackArrow = true,
                navActions = {
                    SaveTextButtonContent(
                        isLoading = viewModel.updateScales.value.isLoading,
                        onClick = {
                            viewModel.saveScalesData(id!!)
                        }
                    )
                }
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
                        text = "Type",
                        style = MaterialTheme.typography.labelMedium
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewModel.scalesKindType.value.text,
                        onValueChange = {
                            viewModel.setScalesKindType(it)
                        },
                        colors = TextFieldDefaults.colors(),
                        placeholder = {
                            Text(
                                text = "Type",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isError = updateScalesState.error != null
                    )

                    if (updateScalesState.error != null) {
                        Text(
                            text = updateScalesState.error,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SaveTextButtonContent(
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    Box {
        if (isLoading) {
            LoadingStateComponent()
        } else {
            Text(
                modifier = Modifier.clickable {
                    onClick()
                },
                text = "Save",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontFamily = fontFamily
            )
        }
    }
}