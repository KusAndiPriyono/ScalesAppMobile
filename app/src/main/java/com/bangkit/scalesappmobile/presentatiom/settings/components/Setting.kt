package com.bangkit.scalesappmobile.presentatiom.settings.components

import com.bangkit.scalesappmobile.R

data class Setting(
    val title: String,
    val icon: Int
)

val settingsOptions = listOf(
    Setting(
        title = "Akun Setting",
        icon = R.drawable.ic_account_circle
    ),
    Setting(
        title = "Notifikasi",
        icon = R.drawable.ic_notifications
    ),
    Setting(
        title = "Edit Tema",
        icon = R.drawable.dark_mode
    ),
    Setting(
        title = "Berbagi Aplikasi dengan Teman",
        icon = R.drawable.ic_share
    )
)
