package com.bangkit.scalesappmobile.domain.usecase.scales

import com.bangkit.scalesappmobile.domain.repository.CreateScalesRepository
import javax.inject.Inject

class CreateNewScalesUseCase @Inject constructor(
    private val createScalesRepository: CreateScalesRepository,
) {
    suspend operator fun invoke(
        token: String,
        brand: String,
        calibrationDate: String,
        calibrationPeriod: Int,
        calibrationPeriodInYears: Int,
        equipmentDescription: String,
        kindType: String,
        location: String,
        imageCover: String,
        measuringEquipmentIdNumber: String,
        name: String,
        nextCalibrationDate: String,
        parentMachineOfEquipment: String,
        rangeCapacity: Int,
        serialNumber: String,
        unit: String,
    ) = createScalesRepository.createNewScales(
        token = token,
        brand = brand,
        calibrationDate = calibrationDate,
        calibrationPeriod = calibrationPeriod,
        calibrationPeriodInYears = calibrationPeriodInYears,
        equipmentDescription = equipmentDescription,
        kindType = kindType,
        location = location,
        imageCover = imageCover,
        measuringEquipmentIdNumber = measuringEquipmentIdNumber,
        name = name,
        nextCalibrationDate = nextCalibrationDate,
        parentMachineOfEquipment = parentMachineOfEquipment,
        rangeCapacity = rangeCapacity,
        serialNumber = serialNumber,
        unit = unit,
    )
}