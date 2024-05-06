package com.bangkit.scalesappmobile.presentatiom.createscales

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.bangkit.scalesappmobile.util.UiEvents
import com.bangkit.scalesappmobile.util.formatDate
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun CreateScalesScreen(
    navigator: CreateScalesNavigator,
    viewModel: CreateScalesViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val brand by viewModel.scalesBrand
    val calibrationDate by viewModel.scalesCalibrationDate
    val equipmentDescription by viewModel.scalesEquipmentDescription
    val kindType by viewModel.scalesKindType
    val location by viewModel.scalesLocation
    val name by viewModel.scalesName
    val nextCalibrationDate by viewModel.scalesNextCalibrationDate
    val parentMachineOfEquipment by viewModel.scalesParentMachineOfEquipment
    val serialNumber by viewModel.scalesSerialNumber
    val unit by viewModel.scalesUnit
    val createScalesState by viewModel.createScaleState
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    snackbarHostState.showSnackbar(
                        message = event.message, duration = SnackbarDuration.Short
                    )
                }

                is UiEvents.NavigationEvent -> {
                    navigator.popBackStack()
                }
            }
        }
    }

    CreateScreenContent(
        snackbarHostState = snackbarHostState,
        brand = brand,
        calibrationDate = calibrationDate,
        calibrationPeriod = 0,
        equipmentDescription = equipmentDescription,
        kindType = kindType,
        location = location,
        imageCover = Uri.EMPTY,
        name = name,
        nextCalibrationDate = nextCalibrationDate,
        parentMachineOfEquipment = parentMachineOfEquipment,
        rangeCapacity = 0,
        serialNumber = serialNumber,
        unit = unit,
        createScalesState = createScalesState,
        onCurrentBrandChange = {
            viewModel.setScalesBrand(it)
        },
        onCurrentCalibrationDateChange = {
            viewModel.setScalesCalibrationDate(it)
        },
        onCurrentCalibrationPeriodChange = {
            Int
        },
        onCurrentEquipmentDescriptionChange = {
            viewModel.setScalesEquipmentDescription(it)
        },
        onCurrentKindTypeChange = {
            viewModel.setScalesKindType(it)
        },
        onCurrentLocationChange = {
            viewModel.setScalesLocation(it)
        },
        onCurrentImageCoverChange = {
            Uri.EMPTY
        },
        onCurrentNameChange = {
            viewModel.setScalesName(it)
        },
        onCurrentNextCalibrationDateChange = {
            viewModel.setScalesNextCalibrationDate(it)
        },
        onCurrentParentMachineOfEquipmentChange = {
            viewModel.setScalesParentMachineOfEquipment(it)
        },
        onCurrentRangeCapacityChange = {
            Int
        },
        onCurrentSerialNumberChange = {
            viewModel.setScalesSerialNumber(it)
        },
        onCurrentUnitChange = {
            viewModel.setScalesUnit(it)
        },
        onClickNavigateBack = {
            navigator.popBackStack()
        },
        onClickCreate = {
            viewModel.createScales()
            keyboardController?.hide()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun CreateScreenContent(
    snackbarHostState: SnackbarHostState,
    brand: TextFieldState,
    calibrationDate: TextFieldState,
    calibrationPeriod: Int,
    equipmentDescription: TextFieldState,
    kindType: TextFieldState,
    location: TextFieldState,
    imageCover: Uri,
    name: TextFieldState,
    nextCalibrationDate: TextFieldState,
    parentMachineOfEquipment: TextFieldState,
    rangeCapacity: Int,
    serialNumber: TextFieldState,
    unit: TextFieldState,
    createScalesState: CreateScalesState,
    onCurrentBrandChange: (String) -> Unit,
    onCurrentCalibrationDateChange: (String) -> Unit,
    onCurrentCalibrationPeriodChange: (Int) -> Unit,
    onCurrentEquipmentDescriptionChange: (String) -> Unit,
    onCurrentKindTypeChange: (String) -> Unit,
    onCurrentLocationChange: (String) -> Unit,
    onCurrentImageCoverChange: (Uri) -> Unit,
    onCurrentNameChange: (String) -> Unit,
    onCurrentNextCalibrationDateChange: (String) -> Unit,
    onCurrentParentMachineOfEquipmentChange: (String) -> Unit,
    onCurrentRangeCapacityChange: (Int) -> Unit,
    onCurrentSerialNumberChange: (String) -> Unit,
    onCurrentUnitChange: (String) -> Unit,
    onClickNavigateBack: () -> Unit,
    onClickCreate: () -> Unit,
) {
    var currentDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var currentTime by remember {
        mutableStateOf(LocalTime.now())
    }
    val dateDialog = rememberUseCaseState()
    val timeDialog = rememberUseCaseState()
    var dateTimeUpdated by remember { mutableStateOf(false) }
    val formattedCalibrationDate = formatDate(LocalDate.parse(calibrationDate.text))


    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }, topBar = {
        IconButton(
            onClick = onClickNavigateBack
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
            )
        }
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Buat Data Scales Baru",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = fontFamily,
                    )
                    Text(
                        text = "Hanya untuk admin",
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = fontFamily
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.padding(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    elevation = CardDefaults.elevatedCardElevation(4.dp)
                ) {
                    OutlinedTextField(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        value = calibrationDate.text,
                        onValueChange = { onCurrentCalibrationDateChange(it) },
                        label = {
                            Text(text = "Tanggal Kalibrasi")
                        },
                        trailingIcon = {
                            if (dateTimeUpdated) {
                                IconButton(
                                    onClick = {
                                        currentDate = LocalDate.now()
                                        currentTime = LocalTime.now()
                                        dateTimeUpdated = false
                                        onCurrentCalibrationDateChange(
                                            ZonedDateTime.of(
                                                currentDate,
                                                currentTime,
                                                ZoneId.systemDefault()
                                            ).toString()
                                        )
                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "CLose Icon",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = {
                                        dateDialog.show()
                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Date Range Icon",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    )
                    CalendarDialog(
                        state = dateDialog,
                        selection = CalendarSelection.Date { localDate ->
                            currentDate = localDate
                            timeDialog.show()
                        },
                        config = CalendarConfig(monthSelection = true, yearSelection = true)
                    )

                    ClockDialog(
                        state = timeDialog,
                        selection = ClockSelection.HoursMinutes { hours, minutes ->
                            currentTime = LocalTime.of(hours, minutes)
                            dateTimeUpdated = true
                            onCurrentCalibrationDateChange(
                                ZonedDateTime.of(
                                    currentDate,
                                    currentTime,
                                    ZoneId.systemDefault()
                                ).toString()
                            )
                        }
                    )
                }
            }
        }
    }
}
