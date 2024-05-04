package com.bangkit.scalesappmobile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.onboarding.GetIfUserIsLoggedInUseCase
import com.bangkit.scalesappmobile.domain.usecase.onboarding.GetOnBoarding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getOnBoarding: GetOnBoarding,
    private val getIfUserIsLoggedInUseCase: GetIfUserIsLoggedInUseCase
) : ViewModel() {

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    val isLoggedIn = getIfUserIsLoggedInUseCase().map { it != null }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false,
    )

    init {
        getOnBoarding().onEach { onBoarding ->
            _splashCondition.value = onBoarding == true
            delay(300)
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}