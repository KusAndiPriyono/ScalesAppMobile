package com.bangkit.scalesappmobile.navigation

import com.bangkit.scalesappmobile.R
import com.ramcosta.composedestinations.spec.NavGraphSpec

sealed class BottomNavItem(var title: String, var icon: Int, var screen: NavGraphSpec) {
    data object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.ic_home,
        screen = NavGraphs.home
    )

    data object Settings : BottomNavItem(
        title = "Settings",
        icon = R.drawable.ic_settings,
        screen = NavGraphs.settings
    )
}
