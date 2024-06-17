package com.bangkit.scalesappmobile.navigation

import com.bangkit.scalesappmobile.R
import com.ramcosta.composedestinations.spec.NavGraphSpec

sealed class BottomNavItem(var title: String, var icon: Int, var screen: NavGraphSpec) {
    data object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.ic_home,
        screen = NavGraphs.home
    )

    data object Kalibrasi : BottomNavItem(
        title = "Kalibrasi",
        icon = R.drawable.ic_folder,
        screen = NavGraphs.kalibrasi
    )

    data object Schedule : BottomNavItem(
        title = "Schedule",
        icon = R.drawable.ic_schedule,
        screen = NavGraphs.schedule
    )

    data object Notifications : BottomNavItem(
        title = "Notif",
        icon = R.drawable.ic_notifications,
        screen = NavGraphs.notifications
    )

    data object Settings : BottomNavItem(
        title = "Settings",
        icon = R.drawable.ic_settings,
        screen = NavGraphs.settings
    )
}
