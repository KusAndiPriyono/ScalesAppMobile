package com.bangkit.scalesappmobile.presentatiom.auth.state

data class PasswordTextFieldState(
    val text: String = "",
    val error: String? = null,
    val isPasswordVisible: Boolean = true
)
