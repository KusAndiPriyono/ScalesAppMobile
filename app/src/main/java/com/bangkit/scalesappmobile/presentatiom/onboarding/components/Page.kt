package com.bangkit.scalesappmobile.presentatiom.onboarding.components

import androidx.annotation.DrawableRes
import com.bangkit.scalesappmobile.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val onBoardingPages = listOf(
    Page(
        title = "Selamat Datang",
        description = "Di aplikasi ScalesApp, tempat terpercaya untuk merekam dan mengelola semua informasi kalibrasi timbangan",
        image = R.drawable.onboarding_1
    ),
    Page(
        title = "Tentang Aplikasi",
        description = "Aplikasi ini dapat dengan mudah melacak riwayat kalibrasi timbangan, dapat menambahkan review, dan melihat semua timbangan yang telah dikalibrasi",
        image = R.drawable.onboarding_2
    ),
    Page(
        title = "Mulai Sekarang",
        description = "Yuk, mulai gunakan aplikasi ScalesApp sekarang juga!",
        image = R.drawable.onboarding_3
    )
)
