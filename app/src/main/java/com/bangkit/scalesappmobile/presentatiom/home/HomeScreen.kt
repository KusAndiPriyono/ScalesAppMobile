package com.bangkit.scalesappmobile.presentatiom.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.presentatiom.common.ScalesList
import com.bangkit.scalesappmobile.presentatiom.home.component.SearchBox
import com.bangkit.scalesappmobile.presentatiom.home.component.StandardToolbar
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.delay


@Destination
@Composable
fun HomeScreen(
    navigator: HomeNavigator,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val scales = viewModel.getScales().collectAsLazyPagingItems()
    val event = viewModel::onEvent
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }


    HomeScreenContent(
        scales = scales,
        state = state,
        userRole = "admin",
        snackbarHostState = snackbarHostState,
        event = event,
        navigateToDetails = { scales ->
            navigator.openScalesDetails(id = scales.id)
        },
        onClickSearch = { /*TODO*/ }) {
    }
}


@Composable
private fun HomeScreenContent(
    scales: LazyPagingItems<Scales>,
    userRole: String,
    state: HomeState,
    snackbarHostState: SnackbarHostState,
    event: (HomeEvent) -> Unit,
    navigateToDetails: (Scales) -> Unit,
    onClickSearch: () -> Unit,
    onClickAddScales: () -> Unit,
) {
    val visible = remember {
        mutableStateOf(false)
    }

    if (userRole != state.data?.user?.role) {
        visible.value = true
    } else {
        visible.value = false
    }

    Scaffold(
        topBar = {
            StandardToolbar(
                navigate = {},
                title = {
                    SearchBox(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .fillMaxWidth(),
                        onClick = onClickSearch
                    )
                },
                showBackArrow = false,
                navActions = {}
            )
        },
        floatingActionButton = {
            if (visible.value) {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = onClickAddScales
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                        AnimatedVisibility(visible = true) {
                            Text(
                                text = "Add Scales",
                                style = MaterialTheme.typography.labelMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val scrollState = rememberScrollState(initial = state.scrollValue)

            // Update the maxScrollingValue
            LaunchedEffect(key1 = scrollState.maxValue) {
                event(HomeEvent.UpdateMaxScrollingValue(scrollState.maxValue))
            }
            // Save the state of the scrolling position
            LaunchedEffect(key1 = scrollState.value) {
                event(HomeEvent.UpdateScrollValue(scrollState.value))
            }
            // Animate the scrolling
            LaunchedEffect(key1 = state.maxScrollingValue) {
                delay(500)
                if (state.maxScrollingValue > 0) {
                    scrollState.animateScrollTo(
                        value = state.maxScrollingValue,
                        animationSpec = infiniteRepeatable(
                            tween(
                                durationMillis = (state.maxScrollingValue - state.scrollValue) * 50_000 / state.maxScrollingValue,
                                easing = LinearEasing,
                                delayMillis = 1000
                            )
                        )
                    )
                }
            }

            ScalesList(
                modifier = Modifier.padding(horizontal = 24.dp),
                scales = scales,
                onClick = navigateToDetails
            )
        }
    }
}
