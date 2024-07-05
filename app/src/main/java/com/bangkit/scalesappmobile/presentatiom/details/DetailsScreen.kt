package com.bangkit.scalesappmobile.presentatiom.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.bangkit.scalesappmobile.presentatiom.details.component.ActionButtonDetail
import com.bangkit.scalesappmobile.presentatiom.details.component.CardReviewContent
import com.bangkit.scalesappmobile.presentatiom.details.component.ScalesProperties
import com.bangkit.scalesappmobile.presentatiom.home.HomeNavigator
import com.bangkit.scalesappmobile.presentatiom.home.component.UserRole
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.component.SectionTitle
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
    id: String?,
    navigator: HomeNavigator,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val state = rememberCollapsingToolbarScaffoldState()
    val scalesState = viewModel.details.value
    val reviewsState = viewModel.reviewsState.value
    val userRole by viewModel.getUserRole().collectAsState(initial = UserRole.USER)

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
        },
        userRole = userRole,
        reviewsState = reviewsState,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreenContent(
    scalesState: DetailState,
    reviewsState: ReviewState,
    state: CollapsingToolbarScaffoldState,
    navigateToBack: () -> Unit,
    onClickEditScales: (ScalesDetails) -> Unit,
    onClickCreateDocumentKalibrasi: () -> Unit,
    onClickDeleteScales: () -> Unit,
    userRole: UserRole,
) {
    var isDialogOpened by remember {
        mutableStateOf(false)
    }
    var showAllReviews by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!scalesState.isLoading && scalesState.scalesDetails != null) {
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
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = scalesState.scalesDetails.imageCover)
                                .apply(block = fun ImageRequest.Builder.() {
                                    placeholder(null)
                                }).build()
                        ), contentDescription = null
                    )
                    Text(
                        text = scalesState.scalesDetails.name,
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
                    val filteredReviews =
                        reviewsState.reviews?.data?.filter { it.scale == scalesState.scalesDetails.id }
                    val reviewsToShow =
                        if (showAllReviews) filteredReviews else filteredReviews?.take(1)

                    item {
                        if (textSize.value >= 19) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(0.85f),
                                    text = scalesState.scalesDetails.name,
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
                                ScalesProperties(scales = scalesState.scalesDetails)
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
                                text = "Nomor Alat", style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ), color = Color.Gray
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
                                text = scalesState.scalesDetails.measuringEquipmentIdNumber,
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
                                text = "Nomor Seri", style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ), color = Color.Gray
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
                                text = scalesState.scalesDetails.serialNumber,
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
                                text = "Tanggal Kalibrasi", style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ), color = Color.Gray
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
                            FormatStringToDate(
                                dateString = scalesState.scalesDetails.calibrationDate,
                                color = MaterialTheme.colorScheme.onSurface
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
                                painter = painterResource(id = R.drawable.next_kalibrasi),
                                contentDescription = null,
                            )
                            Text(
                                text = "Kalibrasi Selanjutnya", style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ), color = Color.Gray
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
                            FormatStringToDate(
                                dateString = scalesState.scalesDetails.nextCalibrationDate,
                                color = MaterialTheme.colorScheme.onSurface
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
                                painter = painterResource(id = R.drawable.note),
                                contentDescription = null,
                            )
                            Text(
                                text = "Deskripsi Alat", style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ), color = Color.Gray
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
                                text = scalesState.scalesDetails.equipmentDescription,
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
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SectionTitle(title = "Review")
                            if (!showAllReviews && (filteredReviews?.size ?: 0) > 1) {
                                TextButton(onClick = { showAllReviews = true }) {
                                    Text(text = "Lihat Semua")
                                }
                            } else if (showAllReviews) {
                                TextButton(onClick = { showAllReviews = false }) {
                                    Text(text = "Tutup")
                                }
                            }
                        }
                    }

                    //ReviewsComponent
                    item {
                        if (reviewsState.isLoading) {
                            LoadingStateComponent()
                        } else if (reviewsState.error != null) {
                            ErrorStateComponent(errorMessage = reviewsState.error)
                        } else {
                            reviewsToShow?.forEach { review ->
                                CardReviewContent(
                                    allReviews = review,
                                    index = reviewsToShow.indexOf(review)
                                )
                            }
                        }
                    }


                    //ActionButtonDetail
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        if (userRole == UserRole.fromString("admin")) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                ActionButtonDetail(
                                    text = "Edit", icon = Icons.Default.Edit, onClick = {
                                        onClickEditScales(scalesState.scalesDetails)
                                    }, modifier = Modifier.width(70.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                ActionButtonDetail(
                                    text = "Create Doc",
                                    icon = Icons.Default.CreateNewFolder,
                                    onClick = {
                                        onClickCreateDocumentKalibrasi()
                                    },
                                    modifier = Modifier.width(150.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                ActionButtonDetail(
                                    text = "Delete",
                                    icon = Icons.Default.Delete,
                                    color = SurprisedColor,
                                    onClick = {
                                        isDialogOpened = true
                                    },
                                    modifier = Modifier.width(70.dp)
                                )
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

    DisplayAlertDialog(title = "Hapus Timbangan",
        message = "Apakah Anda yakin ingin menghapus timbangan ini?",
        dialogOpened = isDialogOpened,
        onDialogClosed = {
            isDialogOpened = false
        },
        onYesClicked = {
            onClickDeleteScales()
            isDialogOpened = false
        })
}