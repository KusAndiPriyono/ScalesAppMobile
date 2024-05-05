package com.bangkit.scalesappmobile.domain.repository

import com.bangkit.scalesappmobile.data.remote.scales.CreateScalesResponse
import com.bangkit.scalesappmobile.util.Resource

interface CreateScalesRepository {

    suspend fun createNewScales(
        token: String,
        brand: String,
        calibrationDate: String,
        calibrationPeriod: Int,
        calibrationPeriodInYears: Int,
        equipmentDescription: String,
        imageCover: String,
        kindType: String,
        location: String,
        measuringEquipmentIdNumber: String,
        name: String,
        nextCalibrationDate: String,
        parentMachineOfEquipment: String,
        rangeCapacity: Int,
        serialNumber: String,
        unit: String,
    ): Resource<CreateScalesResponse>
}