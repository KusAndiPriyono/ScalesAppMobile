package com.bangkit.scalesappmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.scalesappmobile.navigation.BottomNavItem
import com.bangkit.scalesappmobile.navigation.CoreFeatureNavigator
import com.bangkit.scalesappmobile.navigation.NavGraphs
import com.bangkit.scalesappmobile.navigation.StandardScaffold
import com.bangkit.scalesappmobile.navigation.scaleInEnterTransition
import com.bangkit.scalesappmobile.navigation.scaleInPopEnterTransition
import com.bangkit.scalesappmobile.navigation.scaleOutExitTransition
import com.bangkit.scalesappmobile.navigation.scaleOutPopExitTransition
import com.bangkit.scalesappmobile.presentatiom.destinations.HomeScreenDestination
import com.bangkit.scalesappmobile.ui.theme.ScalesAppMobileTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { viewModel.splashCondition.value })
        }
        setContent {
            val isLoggedIn = viewModel.isLoggedIn.collectAsState().value
            ScalesAppMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
//                    NavGraph(startDestination = viewModel.startDestination.value)
                    val navController = rememberNavController()
                    val newBackStackEntry = navController.currentBackStackEntryAsState()
                    val route = newBackStackEntry.value?.destination?.route

                    val bottomBarItems = listOf(
                        BottomNavItem.Home,
                        BottomNavItem.Settings
                    )

                    StandardScaffold(
                        navController = navController,
                        isLoggedIn = isLoggedIn,
                        items = bottomBarItems,
                        showBottomBar = route in listOf(
                            "home/${HomeScreenDestination.route}",
                            "settings/${HomeScreenDestination.route}"
                        )
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            AppNavigation(
                                navController = navController,
                                isLoggedIn = isLoggedIn,
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    }
                }
            }
        }
    }

    @OptIn(
        ExperimentalMaterialNavigationApi::class,
        ExperimentalAnimationApi::class
    )
    @Composable
    internal fun AppNavigation(
        navController: NavHostController,
        modifier: Modifier = Modifier,
        isLoggedIn: Boolean,
    ) {
        val navHostEngine = rememberAnimatedNavHostEngine(
            navHostContentAlignment = Alignment.TopCenter,
            rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
            defaultAnimationsForNestedNavGraph = mapOf(
                NavGraphs.appScales to NestedNavGraphDefaultAnimations(
                    enterTransition = {
                        scaleInEnterTransition()
                    },
                    exitTransition = {
                        scaleOutExitTransition()
                    },
                    popEnterTransition = {
                        scaleInPopEnterTransition()
                    },
                    popExitTransition = {
                        scaleOutPopExitTransition()
                    }
                ),
                NavGraphs.auth to NestedNavGraphDefaultAnimations(
                    enterTransition = {
                        scaleInEnterTransition()
                    },
                    exitTransition = {
                        scaleOutExitTransition()
                    },
                    popEnterTransition = {
                        scaleInPopEnterTransition()
                    },
                    popExitTransition = {
                        scaleOutPopExitTransition()
                    }
                ),
                NavGraphs.home to NestedNavGraphDefaultAnimations(
                    enterTransition = {
                        scaleInEnterTransition()
                    },
                    exitTransition = {
                        scaleOutExitTransition()
                    },
                    popEnterTransition = {
                        scaleInPopEnterTransition()
                    },
                    popExitTransition = {
                        scaleOutPopExitTransition()
                    }
                ),
                NavGraphs.settings to NestedNavGraphDefaultAnimations(
                    enterTransition = {
                        scaleInEnterTransition()
                    },
                    exitTransition = {
                        scaleOutExitTransition()
                    },
                    popEnterTransition = {
                        scaleInPopEnterTransition()
                    },
                    popExitTransition = {
                        scaleOutPopExitTransition()
                    }
                )
            )
        )
        DestinationsNavHost(
            engine = navHostEngine,
            navController = navController,
            navGraph = NavGraphs.root(isLoggedIn = isLoggedIn),
            modifier = modifier,
            dependenciesContainerBuilder = {
                dependency(
                    currentNavigator(
                        isLoggedIn = isLoggedIn
                    )
                )
            }
        )
    }

    private fun DependenciesContainerBuilder<*>.currentNavigator(
        isLoggedIn: Boolean,
    ): CoreFeatureNavigator {
        return CoreFeatureNavigator(
            navGraph = NavGraphs.root(isLoggedIn),
            navController = navController,
        )
    }
}


