package com.bangkit.scalesappmobile.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Form(
    @SerializedName("calibrationMethod")
    val calibrationMethod: String,
    @SerializedName("createdAt")
    val createdAt: Date,
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