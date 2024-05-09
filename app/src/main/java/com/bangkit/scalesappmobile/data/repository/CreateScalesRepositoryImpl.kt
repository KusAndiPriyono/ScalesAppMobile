package com.bangkit.scalesappmobile.data.repository

import com.bangkit.scalesappmobile.data.remote.ScalesApiService
import com.bangkit.scalesappmobile.data.remote.scales.CreateScalesResponse
import com.bangkit.scalesappmobile.domain.model.CreateScalesRequest
import com.bangkit.scalesappmobile.domain.repository.CreateScalesRepository
import com.bangkit.scalesappmobile.domain.repository.ScalesRepository
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class CreateScalesRepositoryImpl @Inject constructor(
    private val scalesApiService: ScalesApiService,
    private val scalesRepository: ScalesRepository,
) : CreateScalesRepository {
    override suspend fun createNewScales(
        brand: String,
        calibrationDate: String,
        calibrationPeriod: Int,
        equipmentDescription: String,
        imageCover: String,
        kindType: String,
        location: String,
        name: String,
        nextCalibrationDate: String,
        parentMachineOfEquipment: String,
        rangeCapacity: Int,
        serialNumber: String,
        unit: String,
    ): Resource<CreateScalesResponse> {
        return safeApiCall(Dispatchers.IO) {
            scalesApiService.createNewScales(
                createScalesRequest = CreateScalesRequest(
                    brand = brand,
                    calibrationDate = calibrationDate,
                    calibrationPeriod = calibrationPeriod,
                    equipmentDescription = equipmentDescription,
                    imageCover = imageCover,
                    kindType = kindType,
                    location = location,
                    name = name,
                    nextCalibrationDate = nextCalibrationDate,
                    parentMachineOfEquipment = parentMachineOfEquipment,
                    rangeCapacity = rangeCapacity,
                    serialNumber = serialNumber,
                    unit = unit,
                    id = scalesRepository.getScales().first().toString()
                )
            )
        }
    }

    override suspend fun uploadImage(imageCover: MultipartBody.Part): Resource<CreateScalesResponse> {
        return safeApiCall(Dispatchers.IO) {
            val requestBody = "imageCover".toRequestBody("multipart/form-data".toMediaTypeOrNull())
            scalesApiService.uploadImageScales(
                imageCover = imageCover,
                requestBody = requestBody
            )
        }
    }
}