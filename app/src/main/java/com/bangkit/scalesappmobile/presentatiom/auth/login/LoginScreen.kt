package com.bangkit.scalesappmobile.presentatiom.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.presentatiom.auth.AuthNavigator
import com.bangkit.scalesappmobile.presentatiom.auth.state.LoginState
import com.bangkit.scalesappmobile.presentatiom.auth.state.PasswordTextFieldState
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.bangkit.scalesappmobile.util.UiEvents
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun LoginScreen(
    navigator: AuthNavigator,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val keyboardController = LocalSoftwareKeyboardController.current
    val loginState = viewModel.loginState.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }

                is UiEvents.NavigationEvent -> {
                    snackbarHostState.showSnackbar(
                        message = "Login successful",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    SignInScreenContent(
        snackbarHostState = snackbarHostState,
        emailState = emailState,
        passwordState = passwordState,
        loginState = loginState,
        onCurrentEmailTextChange = {
            viewModel.setUserName(it)
        },
        onCurrentPasswordTextChange = {
            viewModel.setPassword(it)
        },
        onClickSignIn = {
            keyboardController?.hide()
            viewModel.loginUser()
        },
        onClickForgotPassword = {
            navigator.openForgotPassword()
        },
        onClickDontHaveAccount = {
            navigator.openSignUp()
        },
        onConfirmPasswordToggle = {
            viewModel.togglePasswordVisibility()
        },
        onClickNavigateBack = {
            navigator.popBackStack()
        }
    )
}

@Composable
private fun SignInScreenContent(
    snackbarHostState: SnackbarHostState,
    emailState: TextFieldState,
    passwordState: PasswordTextFieldState,
    loginState: LoginState,
    onCurrentEmailTextChange: (String) -> Unit,
    onCurrentPasswordTextChange: (String) -> Unit,
    onClickSignIn: () -> Unit,
    onClickForgotPassword: () -> Unit,
    onClickDontHaveAccount: () -> Unit,
    onConfirmPasswordToggle: (Boolean) -> Unit,
    onClickNavigateBack: () -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            IconButton(onClick = onClickNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Selamat Datang!",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Login ke akun Anda untuk terus menikmati layanan kami",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(64.dp))

                Column {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = emailState.text,
                        onValueChange = {
                            onCurrentEmailTextChange(it)
                        },
                        label = {
                            Text(text = "Email")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        ),
                        isError = emailState.error != null

                    )
                    if (emailState.error != "") {
                        Text(
                            text = emailState.error ?: "",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = passwordState.text,
                        onValueChange = {
                            onCurrentPasswordTextChange(it)
                        },
                        label = {
                            Text(text = "Password")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        isError = passwordState.error != null,
                        visualTransformation = if (passwordState.isPasswordVisible) {
                            PasswordVisualTransformation()
                        } else {
                            VisualTransformation.None
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    onConfirmPasswordToggle(!passwordState.isPasswordVisible)
                                },
                                modifier = Modifier.semantics {
                                    testTag = "PasswordToggle"
                                }
                            ) {
                                Icon(
                                    imageVector = if (passwordState.isPasswordVisible) {
                                        Icons.Filled.VisibilityOff
                                    } else {
                                        Icons.Filled.Visibility
                                    },
                                    contentDescription = if (passwordState.isPasswordVisible) {
                                        "Sembunyikan Password"
                                    } else {
                                        "Tampilkan Password"
                                    }
                                )
                            }
                        }
                    )
                    if (passwordState.error != "") {
                        Text(
                            text = passwordState.error ?: "",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onClickForgotPassword) {
                        Text(text = "Lupa password?")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onClickSignIn,
                    shape = RoundedCornerShape(8)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        text = "Masuk",
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                TextButton(
                    onClick = onClickDontHaveAccount,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Belum punya akun?")
                            append(" ")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Daftar")
                            }
                        },
                        fontFamily = fontFamily,
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box {
                        if (loginState.isLoading) {
                            LoadingStateComponent()
                        }
                    }
                }
            }
        }
    }
}