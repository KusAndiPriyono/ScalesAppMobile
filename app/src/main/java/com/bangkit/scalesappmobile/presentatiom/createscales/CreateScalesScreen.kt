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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
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
        })
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
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            IconButton(
                onClick = onClickNavigateBack
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(), contentPadding = PaddingValues(16.dp)
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
                Spacer(modifier = Modifier.padding(24.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = brand.text,
                    onValueChange = { onCurrentBrandChange(it) },
                    label = {
                        Text(text = "Brand")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = calibrationDate.text,
                    onValueChange = { onCurrentCalibrationDateChange(it) },
                    label = {
                        Text(text = "Tanggal Kalibrasi")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = calibrationPeriod.toString(),
                    onValueChange = { onCurrentCalibrationPeriodChange(it.toInt()) },
                    label = {
                        Text(text = "Periode Kalibrasi")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Number,
                        autoCorrect = true
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = equipmentDescription.text,
                    onValueChange = { onCurrentEquipmentDescriptionChange(it) },
                    label = {
                        Text(text = "Deskripsi Alat")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = kindType.text,
                    onValueChange = { onCurrentKindTypeChange(it) },
                    label = {
                        Text(text = "Tipe Alat")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = location.text,
                    onValueChange = { onCurrentLocationChange(it) },
                    label = {
                        Text(text = "Lokasi Alat")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    )
                )
            }
//            item {
//                Spacer(modifier = Modifier.padding(8.dp))
//                OutlinedTextField(
//                    modifier = Modifier.fillMaxWidth(),
//                    value = imageCover.toString(),
//                    onValueChange = { onCurrentKindTypeChange(it) },
//                    label = {
//                        Text(text = "Tipe Alat")
//                    },
//                    keyboardOptions = KeyboardOptions(
//                        capitalization = KeyboardCapitalization.Words,
//                        keyboardType = KeyboardType.Email,
//                        autoCorrect = true
//                    )
//                )
//            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name.text,
                    onValueChange = { onCurrentNameChange(it) },
                    label = {
                        Text(text = "Nama Jenis Timbangan")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = nextCalibrationDate.text,
                    onValueChange = { onCurrentNextCalibrationDateChange(it) },
                    label = {
                        Text(text = "Kalibrasi Selanjutnya")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = parentMachineOfEquipment.text,
                    onValueChange = { onCurrentParentMachineOfEquipmentChange(it) },
                    label = {
                        Text(text = "Mesin Induk Alat")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = rangeCapacity.toString(),
                    onValueChange = { onCurrentRangeCapacityChange(it.toInt()) },
                    label = {
                        Text(text = "Kapasitas Timbangan")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = serialNumber.text,
                    onValueChange = { onCurrentSerialNumberChange(it) },
                    label = {
                        Text(text = "Nomor Seri Alat")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = unit.text,
                    onValueChange = { onCurrentUnitChange(it) },
                    label = {
                        Text(text = "Satuan Timbangan")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onClickCreate, shape = RoundedCornerShape(8)) {
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
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Box {
                        if (createScalesState.isLoading) {
                            Spacer(modifier = Modifier.height(16.dp))
                            LoadingStateComponent()
                        }
                    }
                }
            }
        }
    }
}