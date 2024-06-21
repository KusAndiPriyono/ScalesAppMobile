package com.bangkit.scalesappmobile.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Form(
    @SerializedName("approval")
    val approval: String,
    @SerializedName("calibrationMethod")
    val calibrationMethod: String,
    @SerializedName("createdAt")
    val createdAt: Date,
//    @SerializedName("_id")
//    val id: String,
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
//    @SerializedName("user")
//    val user: User,
    @SerializedName("validUntil")
    val validUntil: String,
    @SerializedName("readingCenter")
    val readingCenter: Double,
    @SerializedName("readingFront")
    val readingFront: Double,
    @SerializedName("readingBack")
    val readingBack: Double,
    @SerializedName("readingLeft")
    val readingLeft: Double,
    @SerializedName("readingRight")
    val readingRight: Double,
    @SerializedName("maxTotalReading")
    val maxTotalReading: Double,
) : Parcelable