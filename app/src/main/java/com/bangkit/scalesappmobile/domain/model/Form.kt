package com.bangkit.scalesappmobile.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Form(
    @SerializedName("approval")
    val approval: String,
    @SerializedName("calibrationMethod")
    val calibrationMethod: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("reference")
    val reference: String,
    @SerializedName("resultCalibration")
    val resultCalibration: String,
    @SerializedName("scale")
    val scale: String,
    @SerializedName("standardCalibration")
    val standardCalibration: String,
    @SerializedName("suhu")
    val suhu: Int,
    @SerializedName("user")
    val user: User,
    @SerializedName("validUntil")
    val validUntil: String,
    @SerializedName("readingCenter")
    val readingCenter: Int,
    @SerializedName("readingFront")
    val readingFront: Int,
    @SerializedName("readingBack")
    val readingBack: Int,
    @SerializedName("readingLeft")
    val readingLeft: Int,
    @SerializedName("readingRight")
    val readingRight: Int,
    @SerializedName("maxTotalReading")
    val maxTotalReading: Int,
) : Parcelable