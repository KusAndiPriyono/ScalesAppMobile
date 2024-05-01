package com.bangkit.scalesappmobile.presentatiom.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.presentatiom.common.ScalesList
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    scales: LazyPagingItems<Scales>,
    state: HomeState,
    event: (HomeEvent) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Scales) -> Unit
) {
    val titles by remember {
        derivedStateOf {
            if (scales.itemCount > 10) {
                scales.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " \uD83D\uDFE5 ") { it.equipmentDescription }
            } else {
                ""
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(30.dp)
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

//        SearchBar(
//            modifier = Modifier
//                .padding(horizontal = 24.dp)
//                .fillMaxWidth(),
//            text = "",
//            readOnly = true,
//            onValueChange = {},
//            onSearch = {},
//            onClick = navigateToSearch
//        )

        Spacer(modifier = Modifier.height(24.dp))

        val scrollState = rememberScrollState(initial = state.scrollValue)

        Text(
            text = titles, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp)
                .horizontalScroll(scrollState, enabled = false),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

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

        Spacer(modifier = Modifier.height(24.dp))

        ScalesList(
            modifier = Modifier.padding(horizontal = 24.dp),
            scales = scales,
            onClick = navigateToDetails
        )
    }
}