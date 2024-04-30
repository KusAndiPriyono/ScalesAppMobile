package com.bangkit.scalesappmobile.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.bangkit.scalesappmobile.presentatiom.onboarding.OnBoardingScreen
import com.bangkit.scalesappmobile.presentatiom.onboarding.OnBoardingViewModel
import com.bangkit.scalesappmobile.presentatiom.scales_navigator.ScalesNavigator

@Composable
fun NavGraph(
    startDestination: String,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                route = Route.OnBoardingScreen.route
            ) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }
        navigation(
            route = Route.ScalesNavigation.route,
            startDestination = Route.ScalesNavigatorScreen.route
        ) {
            composable(
                route = Route.ScalesNavigatorScreen.route
            ) {
                ScalesNavigator()
            }
        }
    }
}