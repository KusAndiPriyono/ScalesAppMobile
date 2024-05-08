package com.bangkit.scalesappmobile.domain.usecase.scales

import com.bangkit.scalesappmobile.domain.repository.CreateScalesRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class CreateNewScalesUseCase @Inject constructor(
    private val createScalesRepository: CreateScalesRepository,
) {
    suspend operator fun invoke(
        brand: String,
        calibrationDate: String,
        calibrationPeriod: Int,
        equipmentDescription: String,
        kindType: String,
        location: String,
        imageCover: MultipartBody.Part,
        name: String,
        nextCalibrationDate: String,
        parentMachineOfEquipment: String,
        rangeCapacity: Int,
        serialNumber: String,
        unit: String,
    ) = createScalesRepository.createNewScales(
        brand = brand,
        calibrationDate = calibrationDate,
        calibrationPeriod = calibrationPeriod,
        equipmentDescription = equipmentDescription,
        kindType = kindType,
        location = location,
        imageCover = imageCover,
        name = name,
        nextCalibrationDate = nextCalibrationDate,
        parentMachineOfEquipment = parentMachineOfEquipment,
        rangeCapacity = rangeCapacity,
        serialNumber = serialNumber,
        unit = unit,
    )
}