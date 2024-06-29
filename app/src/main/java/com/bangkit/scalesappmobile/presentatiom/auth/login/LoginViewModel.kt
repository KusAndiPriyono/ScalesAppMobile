package com.bangkit.scalesappmobile.presentatiom.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.auth.LoginUserUseCase
import com.bangkit.scalesappmobile.domain.usecase.auth.SaveAccessTokenUseCase
import com.bangkit.scalesappmobile.domain.usecase.auth.SaveUserIdUseCase
import com.bangkit.scalesappmobile.domain.usecase.auth.SaveUserRoleUseCase
import com.bangkit.scalesappmobile.presentatiom.auth.state.LoginState
import com.bangkit.scalesappmobile.presentatiom.auth.state.PasswordTextFieldState
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveUserIdUseCase: SaveUserIdUseCase,
    private val saveUserRoleUseCase: SaveUserRoleUseCase,
    private val loginUserUseCase: LoginUserUseCase,
) : ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState
    fun setUserName(value: String) {
        _emailState.value = _emailState.value.copy(text = value)
    }

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState
    fun setPassword(value: String) {
        _passwordState.value = _passwordState.value.copy(text = value)
    }

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun loginUser() {
        viewModelScope.launch {
            if (emailState.value.text.isEmpty()) {
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "Tolong input email anda")
                )
                return@launch
            }
            if (passwordState.value.text.isEmpty()) {
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "Tolong input password anda")
                )
                return@launch
            }
            _loginState.value = _loginState.value.copy(isLoading = true)

            when (
                val result = loginUserUseCase(
                    email = emailState.value.text.trim(),
                    password = passwordState.value.text.trim()
                )
            ) {
                is Resource.Error -> {
                    _loginState.value = loginState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Terjadi kesalahan"
                    )

                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(message = result.message ?: "Terjadi kesalahan")
                    )
                }

                is Resource.Success -> {
                    _loginState.value = loginState.value.copy(isLoading = false, data = result.data)
                    result.data?.token?.let { saveAccessTokenUseCase(it) }
                    result.data?.user?.id?.let { saveUserIdUseCase(it) }
                    result.data?.user?.role?.let { saveUserRoleUseCase(it) }
                    _eventFlow.emit(
                        UiEvents.NavigationEvent(route = "")
                    )
                }

                else -> {
                    loginState
                }
            }
        }
    }

    fun togglePasswordVisibility() {
        _passwordState.value = _passwordState.value.copy(
            isPasswordVisible = !_passwordState.value.isPasswordVisible
        )
    }
}