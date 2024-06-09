package com.bangkit.scalesappmobile.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("calibrationDate")
    val calibrationDate: String,
    @SerializedName("calibrationPeriod")
    val calibrationPeriod: Int,
    @SerializedName("equipmentDescription")
    val equipmentDescription: String,
    @SerializedName("imageCover")
    val imageCover: String,
    @SerializedName("kindType")
    val kindType: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nextCalibrationDate")
    val nextCalibrationDate: String,
    @SerializedName("parentMachineOfEquipment")
    val parentMachineOfEquipment: String,
    @SerializedName("rangeCapacity")
    val rangeCapacity: Int,
    @SerializedName("serialNumber")
    val serialNumber: String,
    @SerializedName("unit")
    val unit: String,
) : Parcelable
