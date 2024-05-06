package com.bangkit.scalesappmobile.presentatiom.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.presentatiom.auth.AuthNavigator
import com.bangkit.scalesappmobile.presentatiom.auth.state.PasswordTextFieldState
import com.bangkit.scalesappmobile.presentatiom.auth.state.RegisterState
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.bangkit.scalesappmobile.util.UiEvents
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun SignInScreen(
    navigator: AuthNavigator,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val registerState by viewModel.registerState
    val userName by viewModel.usernameState
    val email by viewModel.emailState
    val password by viewModel.passwordState
    val confirmPassword by viewModel.confirmPasswordState
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.eventsFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    snackbarHostState.showSnackbar(
                        message = event.message, duration = SnackbarDuration.Short
                    )
                }

                is UiEvents.NavigationEvent -> {
                    navigator.popBackStack()
                    navigator.openSignIn()
                }
            }
        }
    }

    SignUpScreenContent(snackbarHostState = snackbarHostState,
        userName = userName,
        email = email,
        password = password,
        confirmPassword = confirmPassword,
        registerState = registerState,
        onCurrentNameChange = {
            viewModel.setUsername(it)
        },
        onCurrentEmailChange = {
            viewModel.setEmail(it)
        },
        onCurrentPasswordChange = {
            viewModel.setPassword(it)
        },
        onCurrentConfirmPasswordChange = {
            viewModel.setConfirmPassword(it)
        },
        onClickSignUp = {
            viewModel.registerUser()
            keyboardController?.hide()
        },
        onClickHaveAccount = {
            navigator.openSignIn()
        },
        onPasswordToggle = {
            viewModel.togglePasswordVisibility()
        },
        onConfirmPasswordToggle = {
            viewModel.toggleConfirmPasswordVisibility()
        },
        onClickNavigateToBack = {
            navigator.popBackStack()
        })
}

@Composable
private fun SignUpScreenContent(
    snackbarHostState: SnackbarHostState,
    userName: TextFieldState,
    email: TextFieldState,
    password: PasswordTextFieldState,
    confirmPassword: PasswordTextFieldState,
    registerState: RegisterState,
    onCurrentNameChange: (String) -> Unit,
    onCurrentEmailChange: (String) -> Unit,
    onCurrentPasswordChange: (String) -> Unit,
    onCurrentConfirmPasswordChange: (String) -> Unit,
    onClickSignUp: () -> Unit,
    onClickHaveAccount: () -> Unit,
    onPasswordToggle: (Boolean) -> Unit,
    onConfirmPasswordToggle: (Boolean) -> Unit,
    onClickNavigateToBack: () -> Unit,
) {

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, topBar = {
        IconButton(
            onClick = onClickNavigateToBack
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(), contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Mulai ScalesApp",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = fontFamily
                    )
                    Text(
                        text = "Buat akun baru untuk mulai menggunakan ScalesApp",
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = fontFamily
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(64.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = userName.text,
                    onValueChange = { onCurrentNameChange(it) },
                    label = {
                        Text(
                            text = "Name"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    ),
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email.text,
                    onValueChange = { onCurrentEmailChange(it) },
                    label = {
                        Text(
                            text = "Email"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    ),
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = password.text,
                    onValueChange = { onCurrentPasswordChange(it) },
                    label = {
                        Text(
                            text = "Password"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = true, keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (password.isPasswordVisible) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    trailingIcon = {
                        IconButton(onClick = { onPasswordToggle(!password.isPasswordVisible) },
                            modifier = Modifier.semantics { testTag = "password_toggle" }) {
                            Icon(
                                imageVector = if (password.isPasswordVisible) {
                                    Icons.Filled.VisibilityOff
                                } else {
                                    Icons.Filled.Visibility
                                }, contentDescription = if (password.isPasswordVisible) {
                                    "Hide password"
                                } else {
                                    "Show password"
                                }
                            )
                        }
                    })
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = confirmPassword.text,
                    onValueChange = { onCurrentConfirmPasswordChange(it) },
                    label = {
                        Text(
                            text = "Confirm Password"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = true, keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (confirmPassword.isPasswordVisible) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    trailingIcon = {
                        IconButton(onClick = { onConfirmPasswordToggle(!confirmPassword.isPasswordVisible) },
                            modifier = Modifier.semantics { testTag = "confirm_password_toggle" }) {
                            Icon(
                                imageVector = if (confirmPassword.isPasswordVisible) {
                                    Icons.Filled.VisibilityOff
                                } else {
                                    Icons.Filled.Visibility
                                }, contentDescription = if (confirmPassword.isPasswordVisible) {
                                    "Hide password"
                                } else {
                                    "Show password"
                                }
                            )
                        }
                    })
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onClickSignUp, shape = RoundedCornerShape(8)) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        text = "Daftar Sekarang",
                        textAlign = TextAlign.Center
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                TextButton(onClick = onClickHaveAccount, modifier = Modifier.fillMaxWidth()) {
                    Text(text = buildAnnotatedString {
                        append("Sudah punya akun?")
                        append(" ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("Masuk")
                        }
                    }, fontFamily = fontFamily, textAlign = TextAlign.Center)
                }
            }
            item {
                Row(
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Box {
                        if (registerState.isLoading) {
                            Spacer(modifier = Modifier.height(16.dp))
                            LoadingStateComponent()
                        }
                    }
                }
            }
        }
    }
}