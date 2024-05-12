package com.bangkit.scalesappmobile.presentatiom.createscales

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.presentatiom.createscales.state.DateType
import com.bangkit.scalesappmobile.presentatiom.home.component.StandardToolbar
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.bangkit.scalesappmobile.util.UiEvents
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun NextCreateScalesScreen(
    imageCover: Uri,
    name: String,
    brand: String,
    kindType: String,
    serialNumber: String,
    location: String,
    rangeCapacity: Int,
    unit: String,
    navigator: CreateScalesNavigator,
    viewModel: CreateScalesViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scaffoldState = rememberBottomSheetScaffoldState()

    val calibrationDate = viewModel.scalesCalibrationDate.value
    val calibrationPeriod = viewModel.scalesCalibrationPeriod.value
    val equipmentDescription = viewModel.scalesEquipmentDescription.value
    val nextCalibrationDate = viewModel.scalesNextCalibrationDate.value
    val parentMachineOfEquipment = viewModel.scalesParentMachineOfEquipment.value

    var pickedCalibrationDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedNextCalibrationDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedCalibrationDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd MMM yyyy").format(pickedCalibrationDate)
        }
    }
    val formattedNextCalibrationDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd MMM yyyy").format(pickedNextCalibrationDate)
        }
    }

    var selectedDateType by remember {
        mutableStateOf(DateType.CALIBRATION_DATE)
    }


    val dateDialog = rememberUseCaseState()

    Timber.d("NextCreateScalesScreen: $imageCover")

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }

                is UiEvents.NavigationEvent -> {
                    navigator.navigateBackToHome()
                }
            }
        }
    }

    Scaffold(Modifier.fillMaxSize(), topBar = {
        StandardToolbar(
            navigate = {
                navigator.popBackStack()
            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Create Scales", fontSize = 18.sp)
                    SaveTextButtonContent(
                        isLoading = viewModel.createNewScales.value.isLoading,
                        onClick = {
                            if (calibrationDate.text.isEmpty()) {
                                viewModel.setScalesCalibrationDate(error = "Tanggal Kalibrasi tidak boleh kosong")
                                return@SaveTextButtonContent
                            } else if (nextCalibrationDate.text.isEmpty()) {
                                viewModel.setScalesNextCalibrationDate(error = "Tanggal Kalibrasi tidak boleh kosong")
                                return@SaveTextButtonContent
                            } else if (equipmentDescription.text.isEmpty()) {
                                viewModel.setScalesEquipmentDescription(error = "Deskripsi Alat tidak boleh kosong")
                                return@SaveTextButtonContent
                            } else if (parentMachineOfEquipment.text.isEmpty()) {
                                viewModel.setScalesParentMachineOfEquipment(error = "Mesin Induk Alat tidak boleh kosong")
                                return@SaveTextButtonContent
                            }

                            keyboardController?.hide()
                            viewModel.createNewScales(
                                imageCover = imageCover.toString(),
                                brand = brand,
                                kindType = kindType,
                                serialNumber = serialNumber,
                                location = location,
                                rangeCapacity = rangeCapacity,
                                unit = unit,
                                name = name,
                                calibrationPeriod = calibrationPeriod,
                                calibrationDate = calibrationDate.text,
                                equipmentDescription = equipmentDescription.text,
                                nextCalibrationDate = nextCalibrationDate.text,
                                parentMachineOfEquipment = parentMachineOfEquipment.text,
                            )
                        }
                    )
                }
            },
            showBackArrow = true
        )
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(.5f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Periode Kalibrasi - $calibrationPeriod Bulan",
                        style = MaterialTheme.typography.labelMedium,
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = calibrationPeriod.toString(),
                        onValueChange = { newValue ->
                            viewModel.setScalesCalibrationPeriod(newValue.toIntOrNull() ?: 0)
                        },
                        colors = TextFieldDefaults.colors(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        isError = calibrationPeriod == 0
                    )

                    if (calibrationPeriod == 0) {
                        Text(
                            text = "Periode Kalibrasi tidak boleh 0",
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(.5f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Tanggal Kalibrasi", style = MaterialTheme.typography.labelMedium
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = formattedCalibrationDate,
                            onValueChange = {
                                viewModel.setScalesCalibrationDate(formattedCalibrationDate)
                            },
                            colors = TextFieldDefaults.colors(),
                            trailingIcon = {
                                IconButton(onClick = {
                                    selectedDateType = DateType.CALIBRATION_DATE
                                    dateDialog.show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Date Range Icon",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            },
                            isError = calibrationDate.error != null
                        )

                        if (calibrationDate.error != null) {
                            Text(
                                text = calibrationDate.error,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Tgl Kalibrasi Selanjutnya",
                            style = MaterialTheme.typography.labelMedium
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = formattedNextCalibrationDate,
                            onValueChange = {
                                viewModel.setScalesNextCalibrationDate(formattedNextCalibrationDate)
                            },
                            colors = TextFieldDefaults.colors(),
                            trailingIcon = {
                                IconButton(onClick = {
                                    selectedDateType = DateType.NEXT_CALIBRATION_DATE
                                    dateDialog.show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Date Range Icon",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            },
                            isError = calibrationDate.error != null
                        )

                        if (calibrationDate.error != null) {
                            Text(
                                text = calibrationDate.error,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    CalendarDialog(
                        state = dateDialog,
                        selection = CalendarSelection.Date { date ->
                            when (selectedDateType) {
                                DateType.CALIBRATION_DATE -> {
                                    pickedCalibrationDate = date
                                    viewModel.setScalesCalibrationDate(date.toString())
                                }

                                DateType.NEXT_CALIBRATION_DATE -> {
                                    pickedNextCalibrationDate = date
                                    viewModel.setScalesNextCalibrationDate(date.toString())
                                }
                            }
                        },
                        config = CalendarConfig(
                            monthSelection = true,
                            yearSelection = true
                        )
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Deskripsi Alat", style = MaterialTheme.typography.labelMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = equipmentDescription.text,
                        onValueChange = {
                            viewModel.setScalesEquipmentDescription(it)
                        },
                        colors = TextFieldDefaults.colors(),
                        placeholder = {
                            Text(
                                text = "Deskripsi Alat",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Words
                        ),
                        isError = equipmentDescription.error != null
                    )

                    if (equipmentDescription.error != null) {
                        Text(
                            text = equipmentDescription.error,
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Mesin Induk Alat", style = MaterialTheme.typography.labelMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = parentMachineOfEquipment.text,
                        onValueChange = {
                            viewModel.setScalesParentMachineOfEquipment(it)
                        },
                        colors = TextFieldDefaults.colors(),
                        placeholder = {
                            Text(
                                text = "Mesin Induk Alat",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Words
                        ),
                        isError = parentMachineOfEquipment.error != null
                    )

                    if (parentMachineOfEquipment.error != null) {
                        Text(
                            text = parentMachineOfEquipment.error,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SaveTextButtonContent(
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    Box {
        if (isLoading) {
            LoadingStateComponent()
        } else {
            Text(
                modifier = Modifier.clickable {
                    onClick()
                },
                text = "Save",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontFamily = fontFamily
            )
        }
    }
}


