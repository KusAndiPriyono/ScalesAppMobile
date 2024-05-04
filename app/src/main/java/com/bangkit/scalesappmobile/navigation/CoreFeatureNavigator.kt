package com.bangkit.scalesappmobile.navigation

import androidx.navigation.NavController
import com.bangkit.scalesappmobile.presentatiom.auth.AuthNavigator
import com.bangkit.scalesappmobile.presentatiom.destinations.DetailsScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.ForgotPasswordScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.HomeScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.LandingPageScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.LoginScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.SignInScreenDestination
import com.bangkit.scalesappmobile.presentatiom.home.HomeNavigator
import com.bangkit.scalesappmobile.presentatiom.onboarding.AppNavigator
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.NavGraphSpec

class CoreFeatureNavigator(
    private val navGraph: NavGraphSpec,
    private val navController: NavController,
) : AppNavigator, AuthNavigator, HomeNavigator {
    override fun openLandingPage() {
        navController.navigate(LandingPageScreenDestination within NavGraphs.auth)
    }

    override fun openForgotPassword() {
        navController.navigate(ForgotPasswordScreenDestination within NavGraphs.auth)
    }

    override fun openSignUp() {
        navController.navigate(SignInScreenDestination within NavGraphs.auth)
    }

    override fun openSignIn() {
        navController.navigate(LoginScreenDestination within NavGraphs.auth)
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun openHome() {
        navController.navigate(HomeScreenDestination within navGraph)
    }

    override fun openScalesDetails(id: String?) {
        navController.navigate(
            DetailsScreenDestination(id = id) within NavGraphs.home
        )
    }

}