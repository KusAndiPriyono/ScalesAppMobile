package com.bangkit.scalesappmobile.domain.repository

import com.bangkit.scalesappmobile.data.remote.scales.CreateScalesResponse
import com.bangkit.scalesappmobile.util.Resource
import okhttp3.MultipartBody

interface CreateScalesRepository {

    suspend fun createNewScales(
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
    ): Resource<CreateScalesResponse>
}