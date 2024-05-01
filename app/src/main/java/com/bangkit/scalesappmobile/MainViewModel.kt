package com.bangkit.scalesappmobile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.onboarding.GetIfUserIsLoggedInUseCase
import com.bangkit.scalesappmobile.domain.usecase.onboarding.GetOnBoarding
import com.bangkit.scalesappmobile.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getOnBoarding: GetOnBoarding,
    private val getIfUserIsLoggedInUseCase: GetIfUserIsLoggedInUseCase
) : ViewModel() {

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    init {
        getOnBoarding().onEach { onBoarding ->
            if (onBoarding == true) {
                _startDestination.value = Route.ScalesNavigation.route
            } else {
                _startDestination.value = Route.AppStartNavigation.route
            }
            delay(300)
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}