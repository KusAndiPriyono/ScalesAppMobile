package com.bangkit.scalesappmobile.presentatiom.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.presentatiom.common.ScalesList
import com.bangkit.scalesappmobile.presentatiom.home.component.SearchBox
import com.bangkit.scalesappmobile.presentatiom.home.component.StandardToolbar
import com.bangkit.scalesappmobile.ui.theme.AngryColor
import com.bangkit.scalesappmobile.ui.theme.fontFamily
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

    HomeScreenContent(scales = scales,
        state = state,
        snackbarHostState = snackbarHostState,
        event = event,
        navigateToDetails = { id ->
            navigator.openScalesDetails(id = id.id)
        },
        onClickSearch = {

        },
        onClickAddScales = {
            navigator.openCreateScales()
        })
}


@Composable
private fun HomeScreenContent(
    scales: LazyPagingItems<Scales>,
    state: HomeState,
    snackbarHostState: SnackbarHostState,
    event: (HomeEvent) -> Unit,
    navigateToDetails: (Scales) -> Unit,
    onClickSearch: () -> Unit,
    onClickAddScales: () -> Unit,
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
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(34.dp))
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .fillMaxHeight(0.35f),
                elevation = CardDefaults.elevatedCardElevation(4.dp),
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Welcome to Scales App",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = "Scales List",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = fontFamily
            )
            ScalesList(
                modifier = Modifier.padding(horizontal = 24.dp),
                scales = scales,
                onClick = navigateToDetails
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val lazyPagingItems: LazyPagingItems<Scales> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            object : PagingSource<Int, Scales>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Scales> {
                    return LoadResult.Page(
                        data = listOf(
                            Scales(
                                id = "1",
                                name = "Scale 1",
                                brand = "Brand 1",
                                kindType = "Kind 1",
                                location = "Location 1",
                                status = "Status 1",
                                imageCover = "Image 1",
                                slug = "Slug 1",
                                v = 1,
                                unit = "Unit 1",
                                serialNumber = "Serial 1",
                                ratingsQuantity = 1,
                                ratingsAverage = 1.0,
                                rangeCapacity = 1,
                                parentMachineOfEquipment = "Parent 1",
                                nextCalibrationDate = "Next 1",
                                measuringEquipmentIdNumber = "Measuring 1",
                                equipmentDescription = "Equipment 1",
                                calibrationPeriodInYears = 1.0,
                                calibrationPeriod = 1,
                                calibrationDate = "Calibration 1"
                            ),
                        ),
                        prevKey = null,
                        nextKey = null
                    )
                }

                override fun getRefreshKey(state: PagingState<Int, Scales>): Int? {
                    return null
                }
            }
        }
    ).flow.collectAsLazyPagingItems()

    HomeScreenContent(
        scales = lazyPagingItems,
        state = HomeState(),
        snackbarHostState = SnackbarHostState(),
        event = {},
        navigateToDetails = {},
        onClickSearch = {},
        onClickAddScales = {}
    )
}