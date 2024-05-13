package com.bangkit.scalesappmobile.data.repository

import coil.network.HttpException
import com.bangkit.scalesappmobile.data.remote.ScalesApiService
import com.bangkit.scalesappmobile.domain.model.Location
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.domain.repository.ScalesRepository
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import okio.IOException
import javax.inject.Inject

class ScalesRepositoryImpl @Inject constructor(
    private val scalesApiService: ScalesApiService,
) : ScalesRepository {
    override suspend fun getScales(location: String?): Resource<List<Scales>> {
        return try {
            val response = scalesApiService.getAllScales(location)
            Resource.Success(data = response.data)
        } catch (e: IOException) {
            return Resource.Error(message = e.message.toString())
        } catch (e: HttpException) {
            return Resource.Error(message = e.message.toString())
        } catch (e: Exception) {
            return Resource.Error(message = e.message.toString())
        }
    }
//    override fun getScales(location: String?): Flow<PagingData<Scales>> {
//        return Pager(
//            config = PagingConfig(pageSize = 10),
//            pagingSourceFactory = {
//                ScalesPagingSource(scalesApiService = scalesApiService)
//            }
//        ).flow
//    }

    override suspend fun getScalesDetail(id: String): Resource<ScalesDetails> {
        return safeApiCall(Dispatchers.IO) {
            val response = scalesApiService.getScalesDetail(id = id)
            response.data
        }
    }

    override suspend fun getScalesUpdate(token: String, id: String): Resource<ScalesDetails> {
        return safeApiCall(Dispatchers.IO) {
            val response = scalesApiService.updateScales(token = "Bearer $token", id = id)
            response.data
        }
    }

    override suspend fun getScalesLocations(): Resource<List<Location>> {
        return safeApiCall(Dispatchers.IO) {
            val response = scalesApiService.getLocations()
            response.data
        }
    }
}