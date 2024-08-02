package com.bangkit.scalesappmobile.presentatiom.settings

import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.presentatiom.home.component.StandardToolbar
import com.bangkit.scalesappmobile.presentatiom.settings.components.SettingCard
import com.bangkit.scalesappmobile.presentatiom.settings.components.settingsOptions
import com.bangkit.scalesappmobile.util.UiEvents
import com.bangkit.scalesappmobile.util.getAppVersionName
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun SettingsScreen(
    navigator: SettingsNavigator,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }

                is UiEvents.NavigationEvent -> {
                    navigator.logout()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            StandardToolbar(
                navigate = {

                },
                title = {
                    Text(text = "Settings", fontSize = 16.sp)
                },
                showBackArrow = false,
                navActions = {}
            )
        }
    ) { paddingValues ->
        SettingsScreenContent(
            paddingValue = paddingValues,
            context = context,
            logoutState = viewModel.logoutState.value,
            onAkunSettingClick = {

            },
            onNotifikasiClick = {
                navigator.openNotifications()
            },
            onEditTemaClick = {

            },
            logout = {
                viewModel.logoutUser()
            }
        )

    }
}

@Composable
private fun SettingsScreenContent(
    paddingValue: PaddingValues,
    context: Context,
    logout: () -> Unit,
    logoutState: LogoutState,
    onNotifikasiClick: () -> Unit,
    onAkunSettingClick: () -> Unit,
    onEditTemaClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = paddingValue, verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(settingsOptions) { setting ->
                SettingCard(
                    title = setting.title,
                    icon = setting.icon,
                    onClick = { settingsOption ->
                        when (settingsOption) {
                            "Akun Setting" -> {
                                onAkunSettingClick()
                            }

                            "Notifikasi" -> {
                                onNotifikasiClick()
                            }

                            "Edit Tema" -> {
                                onEditTemaClick()
                            }

                            "Berbagi Aplikasi dengan Teman" -> {
                                val appPackageName = context.packageName
                                val sendIntent = Intent()
                                sendIntent.action = Intent.ACTION_SEND
                                sendIntent.putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Download Scales App from Play Store: https://play.google.com/store/apps/details?id=$appPackageName"
                                )
                                sendIntent.type = "text/plain"
                                context.startActivity(sendIntent)
                            }
                        }
                    }
                )
            }


            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (logoutState.isLoading) {
                        LoadingStateComponent()
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {
                        logout()
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = "logout",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(modifier = Modifier.padding(8.dp), text = "Logout")
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "App Version: ${getAppVersionName(context)}",
                modifier = Modifier,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 11.sp
            )

            Text(
                text = "Made with ❤️ by Andi `Universitas Raharja` ",
                modifier = Modifier,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 12.sp
            )
        }
    }
}