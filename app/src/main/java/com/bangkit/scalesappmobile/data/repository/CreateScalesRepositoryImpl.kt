package com.bangkit.scalesappmobile.data.repository

import com.bangkit.scalesappmobile.data.remote.ScalesApiService
import com.bangkit.scalesappmobile.data.remote.scales.CreateScalesResponse
import com.bangkit.scalesappmobile.domain.repository.CreateScalesRepository
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class CreateScalesRepositoryImpl @Inject constructor(
    private val scalesApiService: ScalesApiService,
) : CreateScalesRepository {
    override suspend fun createNewScales(
        brand: String,
        calibrationDate: String,
        calibrationPeriod: Int,
        equipmentDescription: String,
        imageCover: MultipartBody.Part,
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
                brand = brand.toRequestBody(),
                calibrationDate = calibrationDate.toRequestBody(),
                calibrationPeriod = calibrationPeriod.toString().toRequestBody(),
                equipmentDescription = equipmentDescription.toRequestBody(),
                kindType = kindType.toRequestBody(),
                location = location.toRequestBody(),
                name = name.toRequestBody(),
                nextCalibrationDate = nextCalibrationDate.toRequestBody(),
                parentMachineOfEquipment = parentMachineOfEquipment.toRequestBody(),
                rangeCapacity = rangeCapacity.toString().toRequestBody(),
                serialNumber = serialNumber.toRequestBody(),
                unit = unit.toRequestBody(),
                imageCover = imageCover
            )
        }
    }
}