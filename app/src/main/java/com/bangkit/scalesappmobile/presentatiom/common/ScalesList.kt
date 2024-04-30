package com.bangkit.scalesappmobile.presentatiom.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.presentatiom.home.component.ScalesItem

@Composable
fun ScalesList(
    modifier: Modifier = Modifier,
    scales: List<Scales>,
    onClick: (Scales) -> Unit
) {
    if (scales.isEmpty()) {

    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(all = 6.dp)
    ) {
        items(
            count = scales.size,
        ) {
            scales[it]?.let { scales ->
                ScalesItem(scales = scales, onClick = { onClick(scales) })
            }
        }
    }

}

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    scales: LazyPagingItems<Scales>,
    onClick: (Scales) -> Unit
) {

    val handlePagingResult = handlePagingResult(scales)


    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(all = 6.dp)
        ) {
            items(
                count = scales.itemCount,
            ) {
                scales[it]?.let { scales ->
                    ScalesItem(scales = scales, onClick = { onClick(scales) })
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(scales: LazyPagingItems<Scales>): Boolean {
    val loadState = scales.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
//            EmptyScreen(error = error)
            false
        }

        else -> {
            true
        }
    }
}

@Composable
fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        repeat(10) {
//            ArticleCardShimmerEffect(
//                modifier = Modifier.padding(horizontal = MediumPadding1)
//            )
        }
    }
}