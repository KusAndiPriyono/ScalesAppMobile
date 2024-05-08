package com.bangkit.scalesappmobile.domain.usecase.scales

import com.bangkit.scalesappmobile.domain.repository.CreateScalesRepository
import javax.inject.Inject

class CreateNewScalesUseCase @Inject constructor(
    private val createScalesRepository: CreateScalesRepository,
) {
    suspend operator fun invoke(
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
    ) = createScalesRepository.createNewScales(
        brand,
        calibrationDate,
        calibrationPeriod,
        equipmentDescription,
        imageCover,
        kindType,
        location,
        name,
        nextCalibrationDate,
        parentMachineOfEquipment,
        rangeCapacity,
        serialNumber,
        unit,
//        brand = brand,
//        calibrationDate = calibrationDate,
//        calibrationPeriod = calibrationPeriod,
//        equipmentDescription = equipmentDescription,
//        imageCover = imageCover,
//        kindType = kindType,
//        location = location,
//        name = name,
//        nextCalibrationDate = nextCalibrationDate,
//        parentMachineOfEquipment = parentMachineOfEquipment,
//        rangeCapacity = rangeCapacity,
//        serialNumber = serialNumber,
//        unit = unit,
    )
}