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
//        val outputStream = ByteArrayOutputStream()
//        val bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
//        bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
//        val imageBytes = outputStream.toByteArray()
//        val requestFile = imageBytes.toRequestBody("image/*".toMediaTypeOrNull(), 0)
//        val imageCoverPart =
//            MultipartBody.Part.createFormData("imageCover", "imageCover", requestFile)
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
}