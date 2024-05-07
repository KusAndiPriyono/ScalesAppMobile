package com.bangkit.scalesappmobile.presentatiom.createscales

import android.net.Uri
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.presentatiom.createscales.component.BrandScalesTextField
import com.bangkit.scalesappmobile.presentatiom.createscales.component.CalibrationDateTextField
import com.bangkit.scalesappmobile.presentatiom.createscales.component.CalibrationPeriodTextField
import com.bangkit.scalesappmobile.presentatiom.createscales.component.EquipmentDescriptionTextField
import com.bangkit.scalesappmobile.presentatiom.createscales.component.KindTypeTextField
import com.bangkit.scalesappmobile.presentatiom.createscales.component.LocationScalesTextField
import com.bangkit.scalesappmobile.presentatiom.createscales.component.MachineOfEquipmentTextField
import com.bangkit.scalesappmobile.presentatiom.createscales.component.NameScalesTextField
import com.bangkit.scalesappmobile.presentatiom.createscales.component.NextCalibrationDateTextField
import com.bangkit.scalesappmobile.presentatiom.createscales.component.RangeCapacityTextField
import com.bangkit.scalesappmobile.presentatiom.createscales.component.SerialNumberTextField
import com.bangkit.scalesappmobile.presentatiom.createscales.component.UnitScalesTextField
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.bangkit.scalesappmobile.util.UiEvents
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun CreateScalesScreen(
    navigator: CreateScalesNavigator,
    viewModel: CreateScalesViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val brand by viewModel.scalesBrand
    val calibrationDate by viewModel.scalesCalibrationDate
    val calibrationPeriod by viewModel.scalesCalibrationPeriod
    val rangeCapacity by viewModel.scalesRangeCapacity
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
        calibrationPeriod = calibrationPeriod,
        equipmentDescription = equipmentDescription,
        kindType = kindType,
        location = location,
        imageCover = Uri.EMPTY,
        name = name,
        nextCalibrationDate = nextCalibrationDate,
        parentMachineOfEquipment = parentMachineOfEquipment,
        rangeCapacity = rangeCapacity,
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
            viewModel.setScalesCalibrationPeriod(it)
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
            viewModel.setScalesRangeCapacity(it)
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
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    elevation = CardDefaults.elevatedCardElevation(4.dp)
                ) {
                    NameScalesTextField(name = name.text, onCurrentNameChange = onCurrentNameChange)
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BrandScalesTextField(
                            modifier = Modifier.width(190.dp),
                            brand = brand.text,
                            onCurrentBrandChange = onCurrentBrandChange,
                        )
                        KindTypeTextField(
                            modifier = Modifier.width(190.dp),
                            kindType = kindType.text,
                            onCurrentKindTypeChange = onCurrentKindTypeChange
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        RangeCapacityTextField(
                            modifier = Modifier.width(180.dp),
                            rangeCapacity = rangeCapacity,
                            onCurrentRangeCapacityChange = onCurrentRangeCapacityChange
                        )
                        UnitScalesTextField(
                            modifier = Modifier.width(200.dp),
                            unit = unit.text,
                            onCurrentUnitChange = onCurrentUnitChange,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        CalibrationPeriodTextField(
                            modifier = Modifier.width(190.dp),
                            calibrationPeriod = calibrationPeriod,
                            onCurrentCalibrationPeriodChange = onCurrentCalibrationPeriodChange
                        )
                        SerialNumberTextField(
                            modifier = Modifier.width(190.dp),
                            serialNumber = serialNumber.text,
                            onCurrentSerialNumberChange = onCurrentSerialNumberChange,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        CalibrationDateTextField(
                            modifier = Modifier.width(190.dp),
                            calibrationDate = calibrationDate,
                            onCurrentCalibrationDateChange = onCurrentCalibrationDateChange
                        )
                        NextCalibrationDateTextField(
                            modifier = Modifier.width(190.dp),
                            nextCalibrationDate = nextCalibrationDate,
                            onCurrentNextCalibrationDateChange = onCurrentNextCalibrationDateChange
                        )
                    }

                    EquipmentDescriptionTextField(
                        equipmentDescription = equipmentDescription.text,
                        onCurrentEquipmentDescriptionChange = onCurrentEquipmentDescriptionChange
                    )

                    LocationScalesTextField(
                        location = location.text,
                        onCurrentLocationChange = onCurrentLocationChange
                    )
                    MachineOfEquipmentTextField(
                        parentMachineOfEquipment = parentMachineOfEquipment.text,
                        onCurrentParentMachineOfEquipmentChange = onCurrentParentMachineOfEquipmentChange
                    )


                }
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onClickCreate,
                    shape = RoundedCornerShape(8),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        text = "Buat Data Scales",
                        textAlign = TextAlign.Center
                    )
                }
            }
            item {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box {
                        if (createScalesState.isLoading) {
                            LoadingStateComponent()
                        }
                    }
                }
            }
        }
    }
}
