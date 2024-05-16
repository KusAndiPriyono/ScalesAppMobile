package com.bangkit.scalesappmobile.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bangkit.scalesappmobile.domain.model.Scales

class SearchScalesPagingSource(
    private val scalesApiService: ScalesApiService,
    private val brand: String,
) : PagingSource<Int, Scales>() {
    override fun getRefreshKey(state: PagingState<Int, Scales>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    private var totalScalesCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Scales> {
        val page = params.key ?: 1
        return try {
            val scalesResponse = scalesApiService.searchScales(
                brand = brand,
                page = page
            )
            totalScalesCount += scalesResponse.data.size
            val data = scalesResponse.data.distinctBy { it.id }

            LoadResult.Page(
                data = data,
                nextKey = if (totalScalesCount == scalesResponse.results) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}