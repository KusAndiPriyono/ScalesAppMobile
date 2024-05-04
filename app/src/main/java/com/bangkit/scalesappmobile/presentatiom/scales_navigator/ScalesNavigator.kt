//package com.bangkit.scalesappmobile.presentatiom.scales_navigator
//
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import androidx.paging.compose.collectAsLazyPagingItems
//import com.bangkit.scalesappmobile.R
//import com.bangkit.scalesappmobile.domain.model.Scales
//import com.bangkit.scalesappmobile.navigation.Route
//import com.bangkit.scalesappmobile.presentatiom.details.DetailsScreen
//import com.bangkit.scalesappmobile.presentatiom.home.HomeScreen
//import com.bangkit.scalesappmobile.presentatiom.home.HomeViewModel
//import com.bangkit.scalesappmobile.presentatiom.scales_navigator.component.BottomNavigation
//import com.bangkit.scalesappmobile.presentatiom.scales_navigator.component.BottomNavigationItem
//
//@Composable
//fun ScalesNavigator() {
//    val bottomNavigationItems = remember {
//        listOf(
//            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
//            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
////            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark"),
//        )
//    }
//
//    val navController = rememberNavController()
//    val backStackState = navController.currentBackStackEntryAsState().value
//    var selectedItem by rememberSaveable {
//        mutableStateOf(0)
//    }
//    selectedItem = when (backStackState?.destination?.route) {
//        Route.HomeScreen.route -> 0
////        Route.SearchScreen.route -> 1
////        Route.BookmarkScreen.route -> 2
//        else -> 0
//    }
//
//
//    //Hide the bottom navigation when the user is in the details screen
//    val isBottomBarVisible = remember(key1 = backStackState) {
//        backStackState?.destination?.route == Route.HomeScreen.route || backStackState?.destination?.route == "" || backStackState?.destination?.route == ""
//    }
//
//    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
//        if (isBottomBarVisible) {
//            BottomNavigation(
//                items = bottomNavigationItems,
//                selectedItem = selectedItem,
//                onItemClick = { index ->
//                    when (index) {
//                        0 -> navigateToTab(
//                            navController = navController, route = Route.HomeScreen.route
//                        )
//
////                        1 -> navigateToTab(
////                            navController = navController,
//////                            route = Route.SearchScreen.route
////                        )
////
////                        2 -> navigateToTab(
////                            navController = navController,
////                            route = Route.BookmarkScreen.route
////                        )
//                    }
//                })
//        }
//    }) {
//        val bottomPadding = it.calculateBottomPadding()
//        NavHost(
//            navController = navController,
//            startDestination = Route.HomeScreen.route,
//            modifier = Modifier.padding(bottom = bottomPadding)
//        ) {
//            composable(route = Route.HomeScreen.route) { backStackEntry ->
//                val viewModel: HomeViewModel = hiltViewModel()
//                val scales = viewModel.scales.collectAsLazyPagingItems()
//                HomeScreen(scales = scales, navigateToSearch = {
////                        navigateToTab(
////                            navController = navController,
////                            route = Route.SearchScreen.route
////                        )
//                }, navigateToDetails = { scales ->
//                    navigateToDetails(
//                        navController = navController, scales = scales
//                    )
//                }, event = viewModel::onEvent, state = viewModel.state.value
//                )
//            }
//
//            composable(route = Route.DetailsScreen.route) {
//                DetailsScreen()
//            }
//        }
//    }
//}
//
//private fun navigateToTab(navController: NavController, route: String) {
//    navController.navigate(route) {
//        navController.graph.startDestinationRoute?.let { screen_route ->
//            popUpTo(screen_route) {
//                saveState = true
//            }
//        }
//        launchSingleTop = true
//        restoreState = true
//    }
//}
//
//private fun navigateToDetails(navController: NavController, scales: Scales) {
//    navController.currentBackStackEntry?.savedStateHandle?.set("scales", scales)
//    navController.navigate(
//        route = Route.DetailsScreen.route
//    )
//}
