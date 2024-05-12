package com.bangkit.scalesappmobile.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Scales(
    @SerializedName("brand")
    val brand: String,
    @SerializedName("calibrationDate")
    val calibrationDate: String,
    @SerializedName("calibrationPeriod")
    val calibrationPeriod: Int,
    @SerializedName("calibrationPeriodInYears")
    val calibrationPeriodInYears: Double,
    @SerializedName("equipmentDescription")
    val equipmentDescription: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("imageCover")
    val imageCover: String,
    @SerializedName("kindType")
    val kindType: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("measuringEquipmentIdNumber")
    val measuringEquipmentIdNumber: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nextCalibrationDate")
    val nextCalibrationDate: String,
    @SerializedName("parentMachineOfEquipment")
    val parentMachineOfEquipment: String,
    @SerializedName("rangeCapacity")
    val rangeCapacity: Int,
    @SerializedName("ratingsAverage")
    val ratingsAverage: Double,
    @SerializedName("ratingsQuantity")
    val ratingsQuantity: Int,
    @SerializedName("serialNumber")
    val serialNumber: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("__v")
    val v: Int,
) : Parcelable
