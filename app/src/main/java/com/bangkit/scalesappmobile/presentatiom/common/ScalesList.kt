package com.bangkit.scalesappmobile.presentatiom.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.presentatiom.home.component.ScalesCardShimmerEffect


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
            EmptyContent()
            false
        }

        else -> {
            true
        }
    }
}

@Composable
fun ShimmerEffect() {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(all = 24.dp)
    ) {
        repeat(6) {
            item {
                ScalesCardShimmerEffect()
            }
        }
    }
}

@Composable
fun EmptyContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        ErrorStateComponent(errorMessage = "Terlalu banyak request dari IP Anda, coba lagi dalam satu jam!")
    }
}