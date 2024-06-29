package com.bangkit.scalesappmobile.presentatiom.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.presentatiom.common.handlePagingResult
import com.bangkit.scalesappmobile.presentatiom.home.component.Banner
import com.bangkit.scalesappmobile.presentatiom.home.component.LocationSelection
import com.bangkit.scalesappmobile.presentatiom.home.component.ScalesItem
import com.bangkit.scalesappmobile.presentatiom.home.component.UserRole
import com.bangkit.scalesappmobile.presentatiom.home.state.HomeState
import com.bangkit.scalesappmobile.ui.theme.AngryColor
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
    val lazyVerticalGridState = rememberLazyGridState()
    val selectedLocation = viewModel.selectedLocation.collectAsState().value
    val userRole by viewModel.getUserRole().collectAsState(initial = UserRole.USER)


    HomeScreenContent(
        selectedLocation = selectedLocation,
        scales = scales,
        state = state,
        snackbarHostState = snackbarHostState,
        lazyVerticalGridState = lazyVerticalGridState,
        event = event,
        navigateToDetails = { id ->
            navigator.openScalesDetails(id = id.id)
        },
        onClickSearch = {
            navigator.onSearchClick()
        },
        onClickAddScales = {
            navigator.openCreateScales()
        },
        onSelectedLocation = { locationName ->
            viewModel.setSelectedLocation(locationName)
        },
        userRole = userRole
    )
}

@Composable
private fun HomeScreenContent(
    selectedLocation: String?,
    scales: LazyPagingItems<Scales>,
    state: HomeState,
    snackbarHostState: SnackbarHostState,
    lazyVerticalGridState: LazyGridState,
    onSelectedLocation: (location: String) -> Unit,
    event: (HomeEvent) -> Unit,
    userRole: UserRole,
    navigateToDetails: (Scales) -> Unit,
    onClickSearch: () -> Unit,
    onClickAddScales: () -> Unit,
) {

    Scaffold(
        topBar = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Banner(
                    onClick = {
                        onClickSearch()
                    }
                )
            }
        },
        floatingActionButton = {
            if (userRole == UserRole.fromString("admin")) {
                FloatingActionButton(
                    containerColor = AngryColor, onClick = onClickAddScales
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null
                        )
                        AnimatedVisibility(visible = true) {
                            Text(
                                text = "Add Scales",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

        }, snackbarHost = {
            SnackbarHost(snackbarHostState)
        }) { paddingValues ->
        Box(
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
                        value = state.maxScrollingValue, animationSpec = infiniteRepeatable(
                            tween(
                                durationMillis = (state.maxScrollingValue - state.scrollValue) * 50_000 / state.maxScrollingValue,
                                easing = LinearEasing,
                                delayMillis = 1000
                            )
                        )
                    )
                }
            }
            val handlePagingResult = handlePagingResult(scales)
            val filteredScales =
                scales.itemSnapshotList.filter { selectedLocation == "All" || it!!.location == selectedLocation }

            if (handlePagingResult) {
                LazyVerticalGrid(
                    state = lazyVerticalGridState,
                    contentPadding = PaddingValues(16.dp),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    item(span = { GridItemSpan(2) }) {
                        Text(text = "Kategori Lokasi", style = MaterialTheme.typography.titleLarge)
                    }
                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                    item(span = { GridItemSpan(2) }) {
                        LocationSelection(
                            scales = scales,
//                            uniqueLocations = uniqueLocations,
                            onClick = { locationName ->
                                onSelectedLocation(locationName)
                            },
                            selectedLocation = selectedLocation.toString()
                        )
                    }

                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    items(filteredScales) { scales ->
                        scales?.let {
                            ScalesItem(scales = it, onClick = { navigateToDetails(scales) })
                        }
                    }
                }
            }
        }
    }
}