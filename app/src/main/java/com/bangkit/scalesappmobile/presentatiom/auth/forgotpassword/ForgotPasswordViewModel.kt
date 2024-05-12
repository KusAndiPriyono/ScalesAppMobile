package com.bangkit.scalesappmobile.presentatiom.auth.forgotpassword

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.auth.ForgotPasswordUseCase
import com.bangkit.scalesappmobile.presentatiom.auth.state.LoginState
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.bangkit.scalesappmobile.presentatiom.destinations.LoginScreenDestination
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
) : ViewModel() {

    private val _forgotPasswordState = mutableStateOf(LoginState())
    val forgotPasswordState: State<LoginState> = _forgotPasswordState

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState
    fun setEmailState(value: String) {
        _emailState.value = _emailState.value.copy(text = value)
    }

    private val _evenFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _evenFlow.asSharedFlow()

    fun sendPasswordResetLink() {
        viewModelScope.launch {
            if (emailState.value.text.isEmpty()) {
                _evenFlow.emit(UiEvents.SnackbarEvent(message = "Tolong isi email anda"))
                return@launch
            }

            _forgotPasswordState.value = forgotPasswordState.value.copy(isLoading = true)

            when (
                val result = forgotPasswordUseCase(email = emailState.value.text)
            ) {
                is Resource.Error -> {
                    _forgotPasswordState.value = forgotPasswordState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Terjadi error saat mengirim email"
                    )
                    _evenFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "Terjadi error saat mengirim email"
                        )
                    )
                }

                is Resource.Success -> {
                    _forgotPasswordState.value =
                        forgotPasswordState.value.copy(isLoading = false, data = null)
                    _evenFlow.emit(UiEvents.SnackbarEvent(message = "Password reset link berhasil dikirim ke email anda"))
                    _evenFlow.emit(UiEvents.NavigationEvent(LoginScreenDestination.route))
                }

                else -> {
                    forgotPasswordState
                }
            }
        }
    }
}