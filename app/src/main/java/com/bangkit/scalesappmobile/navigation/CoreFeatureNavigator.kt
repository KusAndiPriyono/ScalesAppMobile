package com.bangkit.scalesappmobile.navigation

import android.net.Uri
import androidx.navigation.NavController
import com.bangkit.scalesappmobile.presentatiom.auth.AuthNavigator
import com.bangkit.scalesappmobile.presentatiom.createscales.CreateScalesNavigator
import com.bangkit.scalesappmobile.presentatiom.destinations.CreateScalesScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.DetailsScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.ForgotPasswordScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.HomeScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.LandingPageScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.LoginScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.NextCreateScalesScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.SignInScreenDestination
import com.bangkit.scalesappmobile.presentatiom.home.HomeNavigator
import com.bangkit.scalesappmobile.presentatiom.onboarding.AppNavigator
import com.bangkit.scalesappmobile.presentatiom.settings.SettingsNavigator
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.NavGraphSpec

class CoreFeatureNavigator(
    private val navGraph: NavGraphSpec,
    private val navController: NavController,
) : AppNavigator, AuthNavigator, HomeNavigator, SettingsNavigator, CreateScalesNavigator {
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


    override fun openNextCreateScalesScreen(
        imageCover: Uri,
        name: String,
        brand: String,
        kindType: String,
        serialNumber: String,
        location: String,
        rangeCapacity: Int,
        unit: String
    ) {
        navController.navigate(
            NextCreateScalesScreenDestination(
                imageCover = imageCover,
                name = name,
                brand = brand,
                kindType = kindType,
                serialNumber = serialNumber,
                location = location,
                rangeCapacity = rangeCapacity,
                unit = unit
            ) within NavGraphs.home
        )
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun openCreateScales() {
        navController.navigate(CreateScalesScreenDestination within NavGraphs.home)
    }

    override fun navigateBackToHome() {
        navController.navigate(HomeScreenDestination within NavGraphs.home)
        navController.clearBackStack("home")
    }

    override fun openHome() {
        navController.navigate(HomeScreenDestination within NavGraphs.home)
    }

    override fun openScalesDetails(id: String?) {
        navController.navigate(
            DetailsScreenDestination(id = id) within NavGraphs.home
        )
    }

    override fun logout() {
        navController.navigate(NavGraphs.auth) {
            popUpTo(NavGraphs.root(false).route) {
                inclusive = false
            }
        }
    }
}