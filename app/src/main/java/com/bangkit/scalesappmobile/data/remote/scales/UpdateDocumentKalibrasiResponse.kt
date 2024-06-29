package com.bangkit.scalesappmobile.data.remote.scales

import android.os.Parcelable
import com.bangkit.scalesappmobile.domain.model.UpdateForm
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateDocumentKalibrasiResponse(
    @SerializedName("data")
    val data: UpdateForm,
    @SerializedName("status")
    val status: String,
) : Parcelable
