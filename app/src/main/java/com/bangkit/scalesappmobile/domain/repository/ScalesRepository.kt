package com.bangkit.scalesappmobile.domain.repository

import androidx.paging.PagingData
import com.bangkit.scalesappmobile.domain.model.Scales
import kotlinx.coroutines.flow.Flow

interface ScalesRepository {
    fun getScales(): Flow<PagingData<Scales>>
}