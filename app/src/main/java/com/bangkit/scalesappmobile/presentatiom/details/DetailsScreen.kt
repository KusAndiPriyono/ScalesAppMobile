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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.presentatiom.common.EmptyStateComponent
import com.bangkit.scalesappmobile.presentatiom.common.ErrorStateComponent
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.presentatiom.home.HomeNavigator
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.ramcosta.composedestinations.annotation.Destination
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun DetailsScreen(
    id: String?, navigator: HomeNavigator, viewModel: DetailViewModel = hiltViewModel()
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
            navigator.popBackStack()
        },
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreenContent(
    scalesState: DetailState, state: CollapsingToolbarScaffoldState, navigateToBack: () -> Unit
) {
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
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Nomor Document",
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = fontFamily
                        )
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
                        Text(
                            text = "Tanggal Kalibrasi",
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = fontFamily
                        )
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
                        Text(
                            text = "Kalibrasi Selanjutnya",
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = fontFamily
                        )
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
                        Text(
                            text = "Deskripsi Alat",
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = fontFamily
                        )
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
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormatStringToDate(
    modifier: Modifier = Modifier,
    dateString: String,
) {
    val date = ZonedDateTime.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss")
    val formattedDate = date.format(formatter)
    Text(
        modifier = modifier.padding(4.dp),
        text = formattedDate,
        style = MaterialTheme.typography.bodyMedium,
        fontFamily = fontFamily
    )
}

@Preview(showBackground = true)
@Composable
fun ScalesProperties(
    scales: ScalesDetails = sampleScalesDetails
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScalesProperty(icon = R.drawable.ic_branding, value = scales.brand)
        ScalesProperty(icon = R.drawable.ic_kindtype, value = scales.kindType)
        ScalesProperty(
            icon = R.drawable.ic_branding, value = "${scales.rangeCapacity} ${scales.unit}"
        )
        ScalesProperty(icon = R.drawable.ic_calendar, value = scales.status)
        ScalesProperty(icon = R.drawable.ic_calendar, value = scales.ratingsAverage.toString())
    }
}

@Composable
fun ScalesProperty(
    modifier: Modifier = Modifier, icon: Int, value: String
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .width(70.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewDetailsScreen() {
    DetailScreenContent(scalesState = DetailState(
        isLoading = false, scalesDetails = sampleScalesDetails
    ), state = rememberCollapsingToolbarScaffoldState(), navigateToBack = {})
}

val sampleScalesDetails = ScalesDetails(
    id = "1",
    name = "Scales 1",
    imageCover = "https://www.themealdb.com/images/media/meals/58oia61564916529.jpg",
    brand = "Brand 1",
    calibrationDate = "2021-01-01",
    calibrationPeriod = 1,
    calibrationPeriodInYears = 1,
    equipmentDescription = "Equipment Description 1",
    forms = emptyList(),
    kindType = "Kind Type 1",
    location = "Location 1",
    measuringEquipmentIdNumber = "1",
    nextCalibrationDate = "2022-01-01",
    parentMachineOfEquipment = "Parent Machine Of Equipment 1",
    rangeCapacity = 1,
    ratingsAverage = 1.3,
    ratingsQuantity = 1,
    reviews = emptyList(),
    serialNumber = "1",
    slug = "scales-1",
    status = "Status 1",
    unit = "Unit 1",
)