package com.bangkit.scalesappmobile.presentatiom.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.domain.model.Location
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.presentatiom.common.EmptyStateComponent
import com.bangkit.scalesappmobile.presentatiom.common.ErrorStateComponent
import com.bangkit.scalesappmobile.presentatiom.home.component.ScalesItem
import com.bangkit.scalesappmobile.presentatiom.home.component.SearchBox
import com.bangkit.scalesappmobile.presentatiom.home.component.StandardToolbar
import com.bangkit.scalesappmobile.presentatiom.home.component.SwiperRefreshData
import com.bangkit.scalesappmobile.presentatiom.home.state.HomeState
import com.bangkit.scalesappmobile.presentatiom.home.state.LocationsState
import com.bangkit.scalesappmobile.ui.theme.AngryColor
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.delay
import timber.log.Timber


@Destination
@Composable
fun HomeScreen(
    navigator: HomeNavigator,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val scales = viewModel.scalesState.value
    val event = viewModel::onEvent
    val state = viewModel.state.value
    val locationsState = viewModel.locations.value
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyVerticalGridState = rememberLazyGridState()
    val selectedLocation = viewModel.selectedLocation.value

    HomeScreenContent(
        locationsState = locationsState,
        selectedLocation = selectedLocation,
        scalesState = scales,
        state = state,
        snackbarHostState = snackbarHostState,
        lazyVerticalGridState = lazyVerticalGridState,
        event = event,
        navigateToDetails = { id ->
            navigator.openScalesDetails(id = id.id)
        },
        onClickSearch = {

        },
        onClickAddScales = {
            navigator.openCreateScales()
        },
        onSelectedLocation = { locationName ->
            viewModel.setSelectedLocation(locationName)
            viewModel.getScales(viewModel.selectedLocation.value)
        },
        onRefreshData = {
            viewModel.getScales(viewModel.selectedLocation.value)
        }
    )
}


@Composable
private fun HomeScreenContent(
    locationsState: LocationsState,
    selectedLocation: String,
    scalesState: HomeState,
    state: HomeState,
    snackbarHostState: SnackbarHostState,
    lazyVerticalGridState: LazyGridState,
    onSelectedLocation: (location: String) -> Unit,
    event: (HomeEvent) -> Unit,
    navigateToDetails: (Scales) -> Unit,
    onClickSearch: () -> Unit,
    onClickAddScales: () -> Unit,
    onRefreshData: () -> Unit,
) {

    Scaffold(
        topBar = {
            StandardToolbar(
                navigate = {},
                title = {
                    SearchBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, top = 16.dp),
                        onClick = onClickSearch
                    )
                }, showBackArrow = false, navActions = {})
        },
        floatingActionButton = {
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

        }, snackbarHost = {
            SnackbarHost(snackbarHostState)
        }) { paddingValues ->
        SwiperRefreshData(
            isRefreshingState = scalesState.isLoading,
            onRefreshData = { onRefreshData() }
        ) {
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
                        LocationSelection(
                            state = locationsState,
                            onClick = { locationName ->
                                onSelectedLocation(locationName)
                            },
                            selectedLocation = selectedLocation
                        )
                    }

                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    items(scalesState.scales) { scales ->
                        ScalesItem(
                            scales = scales,
                            onClick = {
                                navigateToDetails(scales)
                            }
                        )
                    }
                }

                if (!scalesState.isLoading && scalesState.error != null && scalesState.scales.isEmpty()) {
                    ErrorStateComponent(errorMessage = scalesState.error)
                }
                if (!scalesState.isLoading && scalesState.error == null && scalesState.scales.isEmpty()) {
                    EmptyStateComponent()
                }
            }
        }
    }
}

@Composable
fun LocationSelection(
    state: LocationsState,
    onClick: (String) -> Unit,
    selectedLocation: String,
) {
    Timber.tag("LocationSelection").d("LocationSelection: %s", state.locations.size)

    val uniqueLocations = state.locations.distinctBy { it.location }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(uniqueLocations) { location ->
            Timber.tag("LocationSelection").d("LocationSelection: %s", location.location)
            LocationItem(
                location = location,
                onClick = {
                    onClick(location.location)
                },
                selectedLocation = selectedLocation
            )
        }
    }
}

@Composable
fun LocationItem(
    location: Location,
    selectedLocation: String,
    onClick: () -> Unit,
) {
    val selected = selectedLocation == location.location
    Card(
        modifier = Modifier
            .width(100.dp)
            .wrapContentHeight()
            .clickable {
                onClick()
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = location.location,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (selected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}