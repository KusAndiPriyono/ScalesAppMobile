package com.bangkit.scalesappmobile.navigation

sealed class Route(
    val route: String,
) {
    object OnBoardingScreen : Route(route = "onBoardingScreen")
    object LoginScreen : Route(route = "loginScreen")
    object SignUpScreen : Route(route = "signUpScreen")
    object HomeScreen : Route(route = "homeScreen")

    object DetailsScreen : Route(route = "detailsScreen")
    object AppStartNavigation : Route(route = "appStartNavigation")
    object ScalesNavigation : Route(route = "scalesNavigation")

    object ScalesNavigatorScreen : Route(route = "scalesNavigator")
}