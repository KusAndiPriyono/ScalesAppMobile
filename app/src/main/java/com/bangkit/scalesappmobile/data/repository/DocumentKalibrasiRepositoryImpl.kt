package com.bangkit.scalesappmobile.data.repository

import coil.network.HttpException
import com.bangkit.scalesappmobile.data.remote.ScalesApiService
import com.bangkit.scalesappmobile.data.remote.scales.UpdateDocumentKalibrasiResponse
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.domain.model.UpdateForm
import com.bangkit.scalesappmobile.domain.repository.DocumentKalibrasiRepository
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import okio.IOException
import javax.inject.Inject

class DocumentKalibrasiRepositoryImpl @Inject constructor(
    private val scalesApiService: ScalesApiService,
) : DocumentKalibrasiRepository {
    override suspend fun getAllDocumentKalibrasi(): Resource<List<AllForm>> {
        return try {
            val response = scalesApiService.getAllForms()
            if (response.status == "success") {
                Resource.Success(data = response.data)
            } else {
                Resource.Error(data = null, message = response.status)
            }
        } catch (e: IOException) {
            return Resource.Error("Error", data = null)
        } catch (e: HttpException) {
            return Resource.Error("Error", data = null)
        } catch (e: Exception) {
            return Resource.Error("Error", data = null)
        }
    }

    override suspend fun updateDocumentKalibrasiDetail(
        id: String,
        updateForm: UpdateForm,
    ): Resource<UpdateDocumentKalibrasiResponse> {
        return safeApiCall(Dispatchers.IO) {
            scalesApiService.updateDocumentKalibrasi(id = id, updateForm = updateForm)
        }
    }

    override suspend fun deleteDocumentKalibrasi(id: String): Resource<Boolean> {
        return safeApiCall(Dispatchers.IO) {
            val response = scalesApiService.deleteDocument(id = id)
            response.toString().isNotEmpty()
        }
    }
}