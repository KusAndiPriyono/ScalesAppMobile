package com.bangkit.scalesappmobile.presentatiom.kalibrasi.component

import androidx.compose.ui.graphics.Color
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.ui.theme.HappyColor
import com.bangkit.scalesappmobile.ui.theme.NeutralColor
import com.bangkit.scalesappmobile.ui.theme.RomanticColor

enum class ApprovalStatus(
    val icon: Int,
    val contentColor: Color,
    val containerColor: Color,
) {
    Approved(
        icon = R.drawable.approve,
        contentColor = Color.Black,
        containerColor = HappyColor
    ),
    Waiting(
        icon = R.drawable.waiting,
        contentColor = Color.White,
        containerColor = NeutralColor
    ),
    Rejected(
        icon = R.drawable.rejected,
        contentColor = Color.White,
        containerColor = RomanticColor
    );

    companion object {
        fun fromString(value: String): ApprovalStatus {
            return when (value.lowercase()) {
                "approved" -> Approved
                "waiting" -> Waiting
                "rejected" -> Rejected
                else -> throw IllegalArgumentException("Unknown value")
            }
        }
    }
}