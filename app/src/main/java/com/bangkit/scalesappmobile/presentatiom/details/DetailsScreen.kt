package com.bangkit.scalesappmobile.presentatiom.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.presentatiom.common.DisplayAlertDialog
import com.bangkit.scalesappmobile.presentatiom.common.EmptyStateComponent
import com.bangkit.scalesappmobile.presentatiom.common.ErrorStateComponent
import com.bangkit.scalesappmobile.presentatiom.common.FormatStringToDate
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.presentatiom.details.component.ScalesProperties
import com.bangkit.scalesappmobile.presentatiom.home.HomeNavigator
import com.bangkit.scalesappmobile.ui.theme.SurprisedColor
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.ramcosta.composedestinations.annotation.Destination
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun DetailsScreen(
    id: String?, navigator: HomeNavigator, viewModel: DetailViewModel = hiltViewModel(),
) {
    val state = rememberCollapsingToolbarScaffoldState()
    val scalesState = viewModel.details.value

    LaunchedEffect(key1 = true, block = {
        if (id != null) {
            viewModel.getDetail(id = id)
        } else {
            navigator.popBackStack()
        }
    })

    DetailScreenContent(
        scalesState = scalesState,
        state = state,
        navigateToBack = {
            navigator.navigateBackToHome()
        },
        onClickEditScales = { scalesDetails ->
            navigator.openUpdateScales(id, scalesDetails)
        },
        onClickCreateDocumentKalibrasi = {
            navigator.openCreateDocumentKalibrasi(id)
        },
        onClickDeleteScales = {
            viewModel.deleteScales(scalesState.scalesDetails?.id ?: "")
            navigator.navigateBackToHome()
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreenContent(
    scalesState: DetailState,
    state: CollapsingToolbarScaffoldState,
    navigateToBack: () -> Unit,
    onClickEditScales: (ScalesDetails) -> Unit,
    onClickCreateDocumentKalibrasi: () -> Unit,
    onClickDeleteScales: () -> Unit,
) {
    var isDialogOpened by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!scalesState.isLoading && scalesState.scalesDetails != null) {
            val scale = scalesState.scalesDetails
            val textSize = (18 + (30 - 18) * state.toolbarState.progress).sp

            CollapsingToolbarScaffold(modifier = Modifier.fillMaxSize(),
                state = state,
                scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
                toolbarModifier = Modifier.background(MaterialTheme.colorScheme.background),
                enabled = true,
                toolbar = {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .height(150.dp)
                            .pin()
                    )

                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .parallax(ratio = 0.5f)
                            .graphicsLayer {
                                alpha = if (textSize.value == 18f) 0f else 1f
                            }, painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(data = scale.imageCover)
                                .apply(block = fun ImageRequest.Builder.() {
                                    placeholder(null)
                                }).build()
                        ), contentDescription = null
                    )
                    Text(
                        text = scale.name,
                        modifier = Modifier
                            .road(Alignment.CenterStart, Alignment.BottomEnd)
                            .padding(60.dp, 16.dp, 16.dp, 16.dp),
                        color = if (textSize.value >= 19) {
                            Color.Transparent
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        },
                        fontSize = textSize
                    )

                    IconButton(onClick = {
                        navigateToBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        horizontal = 16.dp, vertical = 12.dp
                    )
                ) {
                    item {
                        if (textSize.value >= 19) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(0.85f),
                                    text = scale.name,
                                    fontFamily = fontFamily,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        LazyRow {
                            item {
                                ScalesProperties(scales = scale)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            thickness = 0.8.dp,
                            color = Color.Gray
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.nomor_alat),
                                contentDescription = null,
                            )
                            Text(
                                text = "Nomor Alat",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ),
                                color = Color.Gray
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.padding(start = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onBackground)
                            )
                            Text(
                                text = scale.measuringEquipmentIdNumber,
                                modifier = Modifier.padding(3.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = fontFamily
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            thickness = 0.8.dp,
                            color = Color.Gray
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.serial_number),
                                contentDescription = null,
                            )
                            Text(
                                text = "Nomor Seri",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ),
                                color = Color.Gray
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.padding(start = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onBackground)
                            )
                            Text(
                                text = scale.serialNumber,
                                modifier = Modifier.padding(3.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = fontFamily
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            thickness = 0.8.dp,
                            color = Color.Gray
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.tgl_kalibrasi),
                                contentDescription = null,
                            )
                            Text(
                                text = "Tanggal Kalibrasi",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ),
                                color = Color.Gray
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.padding(start = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onBackground)
                            )
                            FormatStringToDate(dateString = scale.calibrationDate)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            thickness = 0.8.dp,
                            color = Color.Gray
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.next_kalibrasi),
                                contentDescription = null,
                            )
                            Text(
                                text = "Kalibrasi Selanjutnya",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ),
                                color = Color.Gray
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.padding(start = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onBackground)
                            )
                            FormatStringToDate(dateString = scale.nextCalibrationDate)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            thickness = 0.8.dp,
                            color = Color.Gray
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.note),
                                contentDescription = null,
                            )
                            Text(
                                text = "Deskripsi Alat",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ),
                                color = Color.Gray
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.padding(start = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onBackground)
                            )
                            Text(
                                text = scale.equipmentDescription,
                                modifier = Modifier.padding(3.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = fontFamily
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            thickness = 0.8.dp,
                            color = Color.Gray
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(70.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(color = SurprisedColor)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    IconButton(onClick = {
                                        onClickEditScales(scalesState.scalesDetails)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit",
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                    Text(
                                        text = "Edit",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontFamily = fontFamily
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Box(
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(150.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(color = SurprisedColor)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    IconButton(onClick = {
                                        onClickCreateDocumentKalibrasi()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.CreateNewFolder,
                                            contentDescription = "Create",
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                    Text(
                                        text = "Buat Dokumen",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontFamily = fontFamily
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Box(
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(70.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(color = SurprisedColor)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    IconButton(onClick = { isDialogOpened = true }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                    Text(
                                        text = "Delete",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontFamily = fontFamily
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (scalesState.isLoading) {
            LoadingStateComponent()
        }

        if (!scalesState.isLoading && scalesState.error != null) {
            ErrorStateComponent(errorMessage = scalesState.error)
        }

        if (!scalesState.isLoading && scalesState.error == null && scalesState.scalesDetails == null) {
            EmptyStateComponent()
        }
    }

    DisplayAlertDialog(
        title = "Hapus Timbangan",
        message = "Apakah Anda yakin ingin menghapus timbangan ini?",
        dialogOpened = isDialogOpened,
        onDialogClosed = {
            isDialogOpened = false
        },
        onYesClicked = {
            onClickDeleteScales()
            isDialogOpened = false
        }
    )
}