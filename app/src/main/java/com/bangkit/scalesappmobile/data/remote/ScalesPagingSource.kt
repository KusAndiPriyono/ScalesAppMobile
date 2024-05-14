package com.bangkit.scalesappmobile.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bangkit.scalesappmobile.domain.model.Scales

class ScalesPagingSource(
    private val scalesApiService: ScalesApiService,
) : PagingSource<Int, Scales>() {
    override fun getRefreshKey(state: PagingState<Int, Scales>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private var totalScalesCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Scales> {
        val page = params.key ?: 1
        return try {
            val scalesResponse =
                scalesApiService.getAllScales(page = page)
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