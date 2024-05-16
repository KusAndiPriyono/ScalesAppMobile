package com.bangkit.scalesappmobile.presentatiom.search.state

import androidx.paging.PagingData
import com.bangkit.scalesappmobile.domain.model.Scales
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val scales: Flow<PagingData<Scales>>? = null,
)
