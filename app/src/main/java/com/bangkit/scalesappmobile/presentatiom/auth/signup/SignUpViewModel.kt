package com.bangkit.scalesappmobile.presentatiom.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.auth.RegisterUserUseCase
import com.bangkit.scalesappmobile.presentatiom.auth.state.PasswordTextFieldState
import com.bangkit.scalesappmobile.presentatiom.auth.state.RegisterState
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.bangkit.scalesappmobile.presentatiom.destinations.LoginScreenDestination
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
) : ViewModel() {

    private val _registerState = mutableStateOf(RegisterState())
    val registerState: State<RegisterState> = _registerState

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState
    fun setEmail(value: String) {
        _emailState.value = _emailState.value.copy(text = value)
    }

    private val _usernameState = mutableStateOf(TextFieldState())
    val usernameState: State<TextFieldState> = _usernameState
    fun setUsername(value: String) {
        _usernameState.value = _usernameState.value.copy(text = value)
    }

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState
    fun setPassword(value: String) {
        _passwordState.value = _passwordState.value.copy(text = value)
    }

    private val _confirmPasswordState = mutableStateOf(PasswordTextFieldState())
    val confirmPasswordState: State<PasswordTextFieldState> = _confirmPasswordState
    fun setConfirmPassword(value: String) {
        _confirmPasswordState.value = _confirmPasswordState.value.copy(text = value)
    }

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    fun registerUser() {
        viewModelScope.launch {
            if (emailState.value.text.isEmpty()) {
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "Tolong isi email anda")
                )
                return@launch
            }

            if (usernameState.value.text.isEmpty()) {
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "Tolong isi username anda")
                )
                return@launch
            }

            if (passwordState.value.text.isEmpty()) {
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "Tolong isi password anda")
                )
                return@launch
            }

            if (confirmPasswordState.value.text.isEmpty()) {
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "Tolong isi konfirmasi password anda")
                )
                return@launch
            }

            if (passwordState.value.text != confirmPasswordState.value.text) {
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "Password tidak sama")
                )
                return@launch
            }

            _registerState.value = _registerState.value.copy(isLoading = true)

            when (
                val result = registerUserUseCase(
                    name = usernameState.value.text,
                    email = emailState.value.text,
                    password = passwordState.value.text,
                    passwordConfirm = confirmPasswordState.value.text
                )
            ) {
                is Resource.Error -> {
                    _registerState.value = _registerState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Terjadi kesalahan yang tidak diketahui"
                    )
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "Terjadi kesalahan yang tidak diketahui"
                        )
                    )
                }

                is Resource.Success -> {
                    _registerState.value = _registerState.value.copy(
                        isLoading = false,
                        data = result.data
                    )
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = "Akun berhasil dibuat, cek email anda untuk verifikasi"
                        )
                    )
                    _eventFlow.emit(UiEvents.NavigationEvent(LoginScreenDestination.route))
                }

                else -> {
                    registerState
                }
            }
        }
    }

    fun togglePasswordVisibility() {
        _passwordState.value =
            _passwordState.value.copy(isPasswordVisible = !passwordState.value.isPasswordVisible)
    }

    fun toggleConfirmPasswordVisibility() {
        _confirmPasswordState.value =
            _confirmPasswordState.value.copy(isPasswordVisible = !confirmPasswordState.value.isPasswordVisible)
    }

}