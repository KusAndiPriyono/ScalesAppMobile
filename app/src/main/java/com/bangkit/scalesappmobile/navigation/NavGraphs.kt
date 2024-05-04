package com.bangkit.scalesappmobile.navigation

import com.bangkit.scalesappmobile.presentatiom.destinations.DetailsScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.ForgotPasswordScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.HomeScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.LandingPageScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.LoginScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.OnBoardingScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.SignInScreenDestination
import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

object NavGraphs {

    val appScales = object : NavGraphSpec {

        override val route = "appScales"

        override val startRoute = OnBoardingScreenDestination routedIn this

        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            OnBoardingScreenDestination
        ).routedIn(this).associateBy { it.route }
    }


    val auth = object : NavGraphSpec {
        override val route = "auth"

        override val startRoute = LandingPageScreenDestination routedIn this

        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            HomeScreenDestination,
            LandingPageScreenDestination,
            LoginScreenDestination,
            SignInScreenDestination,
            ForgotPasswordScreenDestination
        ).routedIn(this).associateBy { it.route }
    }

    val home = object : NavGraphSpec {
        override val route = "home"
        override val startRoute = HomeScreenDestination routedIn this
        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            HomeScreenDestination,
            DetailsScreenDestination
        ).routedIn(this).associateBy { it.route }
    }

    fun root(isLoggedIn: Boolean) = object : NavGraphSpec {
        override val route = "root"
        override val startRoute = if (isLoggedIn) home else appScales
        override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()
        override val nestedNavGraphs = listOf(
            appScales,
            auth,
            home
        )
    }
}