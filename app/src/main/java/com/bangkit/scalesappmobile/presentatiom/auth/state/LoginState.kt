package com.bangkit.scalesappmobile.presentatiom.auth.state

import com.bangkit.scalesappmobile.data.remote.scales.AuthResponse

data class LoginState(
    val isLoading: Boolean = false,
    val data: AuthResponse? = null,
    val error: String? = null,
    val isPasswordVisible: Boolean = false
)
