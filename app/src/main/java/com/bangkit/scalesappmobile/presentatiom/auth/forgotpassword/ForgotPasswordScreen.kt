package com.bangkit.scalesappmobile.presentatiom.auth.forgotpassword

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.presentatiom.auth.AuthNavigator
import com.bangkit.scalesappmobile.presentatiom.auth.state.LoginState
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.bangkit.scalesappmobile.util.UiEvents
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun ForgotPasswordScreen(
    navigator: AuthNavigator,
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val forgotPasswordState = viewModel.forgotPasswordState.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    snackbarHostState.showSnackbar(event.message)
                }

                is UiEvents.NavigationEvent -> {
                    navigator.popBackStack()
                }
            }
        }
    }

    ForgotPasswordScreenContent(
        snackbarHostState = snackbarHostState,
        currentEmailText = viewModel.emailState.value.text,
        forgotPasswordState = forgotPasswordState,
        onCurrentEmailTextChange = {
            viewModel.setEmailState(it)
        },
        onCLickSend = {
            viewModel.sendPasswordResetLink()
            keyboardController?.hide()
        },
        onClickNavigateBack = {
            navigator.popBackStack()
        }
    )
}

@Composable
private fun ForgotPasswordScreenContent(
    snackbarHostState: SnackbarHostState,
    currentEmailText: String,
    forgotPasswordState: LoginState,
    onCurrentEmailTextChange: (String) -> Unit,
    onCLickSend: () -> Unit,
    onClickNavigateBack: () -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            IconButton(onClick = onClickNavigateBack) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues), contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Lupa Password",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = fontFamily
                    )
                    Text(
                        text = "Masukkan email anda untuk mendapatkan link reset password",
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = fontFamily
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = currentEmailText,
                    onValueChange = { onCurrentEmailTextChange(it) },
                    label = { Text(text = "Email") },
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = true,
                        keyboardType = KeyboardType.Email
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onCLickSend, shape = RoundedCornerShape(8)) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        text = "Lanjutkan",
                        textAlign = TextAlign.Center,
                    )
                }
            }

            item {
                if (forgotPasswordState.isLoading) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box {
                            LoadingStateComponent()
                        }
                    }
                }
            }
        }
    }
}