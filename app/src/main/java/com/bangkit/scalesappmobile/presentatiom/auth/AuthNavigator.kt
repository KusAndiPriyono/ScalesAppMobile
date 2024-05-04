package com.bangkit.scalesappmobile.presentatiom.auth

interface AuthNavigator {
    fun openForgotPassword()
    fun openSignUp()
    fun openSignIn()
    fun popBackStack()
}