package com.bangkit.scalesappmobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.OnBoardingScreen.route,
            startDestination = Route.OnBoardingScreen.route
        ) {

        }
        navigation(
            route = Route.LoginScreen.route,
            startDestination = Route.LoginScreen.route
        ) {

        }

    }
}