package com.bangkit.scalesappmobile.domain.model


import com.google.gson.annotations.SerializedName

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
    @SerializedName("__v")
    val v: Int,
    @SerializedName("validUntil")
    val validUntil: String
)