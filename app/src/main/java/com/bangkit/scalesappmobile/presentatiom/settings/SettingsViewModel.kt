package com.bangkit.scalesappmobile.presentatiom.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.auth.LogoutUserUseCase
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUserUseCase: LogoutUserUseCase,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow

    private val _logoutState = mutableStateOf(LogoutState())
    val logoutState: State<LogoutState> = _logoutState

    fun logoutUser() {
        viewModelScope.launch {
            _logoutState.value = LogoutState(isLoading = true)
            logoutUserUseCase()
            // logout user
            _logoutState.value = LogoutState(isLoading = false)
            _eventFlow.emit(UiEvents.NavigationEvent(""))
        }
    }
}

data class LogoutState(
    val isLoading: Boolean = false,
)
