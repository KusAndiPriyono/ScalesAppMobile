package com.bangkit.scalesappmobile.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScalesDetails(
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
    @SerializedName("forms")
    val forms: List<Form>,
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
    @SerializedName("reviews")
    val reviews: List<Review>,
    @SerializedName("serialNumber")
    val serialNumber: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("unit")
    val unit: String,
) : Parcelable
