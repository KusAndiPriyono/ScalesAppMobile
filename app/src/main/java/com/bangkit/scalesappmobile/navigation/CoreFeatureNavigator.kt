package com.bangkit.scalesappmobile.navigation

import android.net.Uri
import androidx.navigation.NavController
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.presentatiom.auth.AuthNavigator
import com.bangkit.scalesappmobile.presentatiom.createscales.CreateScalesNavigator
import com.bangkit.scalesappmobile.presentatiom.destinations.CreateDocumentKalibrasiScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.CreateScalesScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.DetailsScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.ForgotPasswordScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.HomeScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.LandingPageScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.ListKalibrasiScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.LoginScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.NextCreateScalesScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.NotificationScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.ScheduleScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.SearchScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.SettingsScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.SignInScreenDestination
import com.bangkit.scalesappmobile.presentatiom.destinations.UpdateScalesScreenDestination
import com.bangkit.scalesappmobile.presentatiom.home.HomeNavigator
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.KalibrasiNavigator
import com.bangkit.scalesappmobile.presentatiom.onboarding.AppNavigator
import com.bangkit.scalesappmobile.presentatiom.search.SearchNavigator
import com.bangkit.scalesappmobile.presentatiom.settings.SettingsNavigator
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.NavGraphSpec

class CoreFeatureNavigator(
    private val navGraph: NavGraphSpec,
    private val navController: NavController,
) : AppNavigator,
    AuthNavigator,
    HomeNavigator,
    CreateScalesNavigator,
    KalibrasiNavigator,
    SettingsNavigator,
    SearchNavigator {
    override fun openLandingPage() {
        navController.navigate(LandingPageScreenDestination within NavGraphs.auth)
    }

    override fun openForgotPassword() {
        navController.navigate(ForgotPasswordScreenDestination within navGraph)
    }

    override fun openSignUp() {
        navController.navigate(SignInScreenDestination within navGraph)
    }

    override fun openSignIn() {
        navController.navigate(LoginScreenDestination within navGraph)
    }


    override fun openNextCreateScalesScreen(
        imageCover: Uri,
        name: String,
        brand: String,
        kindType: String,
        serialNumber: String,
        location: String,
        rangeCapacity: Int,
        unit: String,
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
            ) within navGraph
        )
    }


    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun openCreateScales() {
        navController.navigate(CreateScalesScreenDestination within navGraph)
    }

    override fun onSearchClick() {
        navController.navigate(SearchScreenDestination within navGraph)
    }

    override fun openKalibrasi() {
        navController.navigate(ListKalibrasiScreenDestination within navGraph)
    }

    override fun openSchedule() {
        navController.navigate(ScheduleScreenDestination within navGraph)
    }

    override fun openNotifications() {
        navController.navigate(NotificationScreenDestination within navGraph)
    }

    override fun openSettings() {
        navController.navigate(SettingsScreenDestination within navGraph)
    }

    override fun navigateBackToHome() {
        navController.navigate(HomeScreenDestination within navGraph)
        navController.clearBackStack("home")
    }

    override fun openCreateDocumentKalibrasi() {
        navController.navigate(CreateDocumentKalibrasiScreenDestination within navGraph)
    }

    override fun openHome() {
        navController.navigate(HomeScreenDestination within navGraph)
    }

    override fun openScalesDetails(id: String?) {
        navController.navigate(
            DetailsScreenDestination(id = id) within navGraph
        )
    }

    override fun openUpdateScales(id: String?, scalesDetails: ScalesDetails?) {
        navController.navigate(
            UpdateScalesScreenDestination(
                id = id,
                scalesDetails = scalesDetails
            ) within navGraph
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

