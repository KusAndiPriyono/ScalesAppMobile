package com.bangkit.scalesappmobile.presentatiom.createdocumentkalibrasi

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.presentatiom.home.HomeNavigator
import com.bangkit.scalesappmobile.presentatiom.home.component.StandardToolbar
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.ramcosta.composedestinations.annotation.Destination
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun CreateDocumentKalibrasiScreen(
    id: String?,
    navigator: HomeNavigator,
    viewModel: CreateDocumentKalibrasiViewModel = hiltViewModel(),
) {

    val calibrationMethod = viewModel.calibrationMethod.value
    val reference = viewModel.reference.value
    val standardCalibration = viewModel.standardCalibration.value
    val suhu = viewModel.suhu.value
    val resultCalibration = viewModel.resultCalibration.value
    val validUntil = viewModel.validUntil.value
    val readingCenter = viewModel.readingCenter.value
    val readingFront = viewModel.readingFront.value
    val readingBack = viewModel.readingBack.value
    val readingLeft = viewModel.readingLeft.value
    val readingRight = viewModel.readingRight.value
    val maxTotalReading = viewModel.maxTotalReading.value

    var pickedValidUntil by remember {
        mutableStateOf(LocalDate.now())
    }

    val formattedValidUntil by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd-MM-yyyy").format(pickedValidUntil)
        }
    }

    val dateDialog = rememberUseCaseState()
    val keyboardController = LocalSoftwareKeyboardController.current


    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
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
                        Text(text = "Create Document Kalibrasi", fontSize = 18.sp)
                        SaveTextButtonContent(
                            isLoading = viewModel.createDocumentKalibrasi.value.isLoading,
                            onClick = {
                                if (calibrationMethod.text.isEmpty()) {
                                    viewModel.setCalibrationMethod(
                                        error = "Metode Kalibrasi tidak boleh kosong"
                                    )
                                }
                                if (reference.text.isEmpty()) {
                                    viewModel.setReference(
                                        error = "Referensi tidak boleh kosong"
                                    )
                                }
                                if (standardCalibration.text.isEmpty()) {
                                    viewModel.setStandardCalibration(
                                        error = "Standar Kalibrasi tidak boleh kosong"
                                    )
                                }
                                if (suhu == 0) {
                                    viewModel.setSuhu(0)
                                }
                                if (resultCalibration.text.isEmpty()) {
                                    viewModel.setResultCalibration(
                                        error = "Hasil Kalibrasi tidak boleh kosong"
                                    )
                                }
                                if (validUntil.text.isEmpty()) {
                                    viewModel.setValidUntil(
                                        error = "Berlaku Sampai tidak boleh kosong"
                                    )
                                }
                                if (readingCenter == 0.0) {
                                    viewModel.setReadingCenter(0.0)
                                }
                                if (readingFront == 0.0) {
                                    viewModel.setReadingFront(0.0)
                                }
                                if (readingBack == 0.0) {
                                    viewModel.setReadingBack(0.0)
                                }
                                if (readingLeft == 0.0) {
                                    viewModel.setReadingLeft(0.0)
                                }
                                if (readingRight == 0.0) {
                                    viewModel.setReadingRight(0.0)
                                }
                                if (maxTotalReading == 0.0) {
                                    viewModel.setMaxTotalReading(0.0)
                                }

                                keyboardController?.hide()
                                viewModel.createDocumentKalibrasi(id = id.toString())
                            }
                        )
                    }

                },
                showBackArrow = true,
                navActions = {}
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Metode Kalibrasi", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = calibrationMethod.text,
                        onValueChange = {
                            viewModel.setCalibrationMethod(it)
                        },
                        colors = TextFieldDefaults.colors(),
                        placeholder = {
                            Text(
                                text = "Metode Kalibrasi",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isError = calibrationMethod.error != null,
                    )
                    if (calibrationMethod.error != null) {
                        Text(
                            text = calibrationMethod.error,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Referensi", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = reference.text,
                        onValueChange = {
                            viewModel.setReference(it)
                        },
                        colors = TextFieldDefaults.colors(),
                        placeholder = {
                            Text(text = "Referensi", style = MaterialTheme.typography.labelMedium)
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isError = reference.error != null,
                    )
                    if (reference.error != null) {
                        Text(
                            text = reference.error,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Standar Kalibrasi", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = standardCalibration.text,
                        onValueChange = {
                            viewModel.setStandardCalibration(it)
                        },
                        colors = TextFieldDefaults.colors(),
                        placeholder = {
                            Text(
                                text = "Standar Kalibrasi",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isError = standardCalibration.error != null,
                    )
                    if (standardCalibration.error != null) {
                        Text(
                            text = standardCalibration.error,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Suhu", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = suhu.toString(),
                        onValueChange = {
                            viewModel.setSuhu(it.toIntOrNull() ?: 0)
                        },
                        colors = TextFieldDefaults.colors(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isError = suhu == 0,
                    )
                    if (suhu == 0) {
                        Text(
                            text = "Suhu tidak boleh bernilai 0",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Hasil Kalibrasi", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = resultCalibration.text,
                        onValueChange = {
                            viewModel.setResultCalibration(it)
                        },
                        colors = TextFieldDefaults.colors(),
                        placeholder = {
                            Text(
                                text = "Hasil Kalibrasi",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isError = resultCalibration.error != null,
                    )
                    if (resultCalibration.error != null) {
                        Text(
                            text = resultCalibration.error,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Berlaku Sampai", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = formattedValidUntil,
                        onValueChange = {
                            viewModel.setValidUntil(formattedValidUntil)
                        },
                        colors = TextFieldDefaults.colors(),
                        trailingIcon = {
                            IconButton(onClick = {
                                dateDialog.show()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Date Range Icon",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        isError = validUntil.error != null
                    )

                    if (validUntil.error != null) {
                        Text(
                            text = validUntil.error,
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
                        pickedValidUntil = date
                        viewModel.setValidUntil(date.toString())
                    },
                    config = CalendarConfig(
                        monthSelection = true,
                        yearSelection = true
                    )
                )
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Nilai Pembacaan Batu Timbangan di Posisi Tengah",
                        style = MaterialTheme.typography.labelMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = readingCenter.toString(),
                        onValueChange = {
                            viewModel.setReadingCenter(it.toDoubleOrNull() ?: 0.0)
                        },
                        colors = TextFieldDefaults.colors(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isError = readingCenter == 0.0,
                    )
                    if (readingCenter == 0.0) {
                        Text(
                            text = "Suhu tidak boleh bernilai 0.0",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Nilai Pembacaan Batu Timbangan di Posisi Depan",
                        style = MaterialTheme.typography.labelMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = readingFront.toString(),
                        onValueChange = {
                            viewModel.setReadingFront(it.toDoubleOrNull() ?: 0.0)
                        },
                        colors = TextFieldDefaults.colors(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isError = readingFront == 0.0,
                    )
                    if (readingFront == 0.0) {
                        Text(
                            text = "Suhu tidak boleh bernilai 0.0",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Nilai Pembacaan Batu Timbangan di Posisi Belakang",
                        style = MaterialTheme.typography.labelMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = readingBack.toString(),
                        onValueChange = {
                            viewModel.setReadingBack(it.toDoubleOrNull() ?: 0.0)
                        },
                        colors = TextFieldDefaults.colors(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isError = readingBack == 0.0,
                    )
                    if (readingBack == 0.0) {
                        Text(
                            text = "Suhu tidak boleh bernilai 0.0",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Nilai Pembacaan Batu Timbangan di Posisi Kiri",
                        style = MaterialTheme.typography.labelMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = readingLeft.toString(),
                        onValueChange = {
                            viewModel.setReadingLeft(it.toDoubleOrNull() ?: 0.0)
                        },
                        colors = TextFieldDefaults.colors(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isError = readingLeft == 0.0,
                    )
                    if (readingLeft == 0.0) {
                        Text(
                            text = "Suhu tidak boleh bernilai 0.0",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Nilai Pembacaan Batu Timbangan di Posisi Kanan",
                        style = MaterialTheme.typography.labelMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = readingRight.toString(),
                        onValueChange = {
                            viewModel.setReadingRight(it.toDoubleOrNull() ?: 0.0)
                        },
                        colors = TextFieldDefaults.colors(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isError = readingRight == 0.0,
                    )
                    if (readingRight == 0.0) {
                        Text(
                            text = "Suhu tidak boleh bernilai 0.0",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Maksimal Total Pembacaan Batu Timbangan",
                        style = MaterialTheme.typography.labelMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = listOf(
                            readingCenter,
                            readingFront,
                            readingBack,
                            readingLeft,
                            readingRight
                        ).maxOrNull().toString(),
                        onValueChange = {
                            viewModel.setMaxTotalReading(it.toDoubleOrNull() ?: 0.0)
                        },
                        colors = TextFieldDefaults.colors(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                    )
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