package com.bangkit.scalesappmobile.presentatiom.update

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.presentatiom.home.component.StandardToolbar
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.KalibrasiNavigator
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.bangkit.scalesappmobile.util.UiEvents
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun UpdateDocKalibrasiScreen(
    id: String?,
    allForm: AllForm?,
    navigator: KalibrasiNavigator,
    viewModel: UpdateDocKalibrasiViewModel = hiltViewModel()
) {
    val updateDocKalibrasiState = viewModel.updateDocState.value
    val scaffoldState = rememberBottomSheetScaffoldState()

    var pickedValidUntil by remember {
        mutableStateOf(LocalDate.now())
    }

    val formattedValidUntil by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd MMMM yyyy").format(pickedValidUntil)
        }
    }

    val dateDialog = rememberUseCaseState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(key1 = true, block = {
        if (id != null && allForm != null) {
            viewModel.loadDocumentKalibrasi(allForm)
        } else {
            navigator.popBackStack()
        }
    })

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }

                is UiEvents.NavigationEvent -> {
                    navigator.navigateBackToKalibrasi()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            StandardToolbar(
                navigate = {
                    navigator.popBackStack()
                },
                title = {
                    Text(text = "Edit Document Kalibrasi", fontSize = 18.sp)
                },
                showBackArrow = true,
                navActions = {
                    SaveTextButtonContent(
                        isLoading = viewModel.updateDocState.value.isLoading,
                        onClick = {
                            viewModel.saveDocumentKalibrasi(id!!, form = allForm!!)
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            //Metode Kalibrasi
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Metode Kalibrasi", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewModel.calibrationMethod.value.text,
                        onValueChange = { item ->
                            viewModel.setCalibrationMethod(item)
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
                        isError = updateDocKalibrasiState.error != null,
                    )
                    if (updateDocKalibrasiState.error != null) {
                        Text(
                            text = updateDocKalibrasiState.error,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            //Referensi
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Referensi", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewModel.reference.value.text,
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
                        isError = updateDocKalibrasiState.error != null,
                    )
                    if (updateDocKalibrasiState.error != null) {
                        Text(
                            text = updateDocKalibrasiState.error,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            //Standar Kalibrasi
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Standar Kalibrasi", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewModel.standardCalibration.value.text,
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
                        isError = updateDocKalibrasiState.error != null,
                    )
                    if (updateDocKalibrasiState.error != null) {
                        Text(
                            text = updateDocKalibrasiState.error,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            //Suhu & Berlaku Sampai
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(.5f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Suhu", style = MaterialTheme.typography.labelMedium)
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = if (viewModel.suhu.value == 0) "" else viewModel.suhu.value.toString(), // Tampilkan string kosong jika nilai adalah 0
                            onValueChange = {
                                viewModel.setSuhu(it.toIntOrNull() ?: 0)
                            },
                            colors = TextFieldDefaults.colors(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            isError = viewModel.updateDocState.value.error != null && viewModel.suhu.value == 0
                        )
                        if (viewModel.updateDocState.value.error != null && viewModel.suhu.value == 0) {
                            Text(
                                text = "Suhu tidak boleh kosong",
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
                        Text(text = "Berlaku Sampai", style = MaterialTheme.typography.labelMedium)
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.validUntil.value.text,
                            onValueChange = {
                                viewModel.setValidUntil(it)
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
                            isError = viewModel.updateDocState.value.error != null,
                        )
                        if (viewModel.updateDocState.value.error != null) {
                            Text(
                                text = viewModel.updateDocState.value.error!!,
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
            }
            //Nilai Pembacaan Batu Timbangan di Posisi Tengah & Depan
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(.5f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Nilai Pembacaan Batu Timbangan di Posisi Tengah",
                            style = MaterialTheme.typography.labelMedium
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = if (viewModel.readingCenter.doubleValue == 0.0) "" else viewModel.readingCenter.doubleValue.toString(), // Tampilkan string kosong jika nilai adalah 0.0
                            onValueChange = {
                                viewModel.setReadingCenter(it)
                            },
                            colors = TextFieldDefaults.colors(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Decimal
                            ),
                            isError = viewModel.updateDocState.value.error != null && viewModel.readingCenter.doubleValue == 0.0,
                        )
                        if (viewModel.updateDocState.value.error != null && viewModel.readingCenter.doubleValue == 0.0) {
                            Text(
                                text = "tidak boleh kosong",
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
                            text = "Nilai Pembacaan Batu Timbangan di Posisi Depan",
                            style = MaterialTheme.typography.labelMedium
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = if (viewModel.readingFront.doubleValue == 0.0) "" else viewModel.readingFront.doubleValue.toString(), // Tampilkan string kosong jika nilai adalah 0.0
                            onValueChange = {
                                viewModel.setReadingFront(it)
                            },
                            colors = TextFieldDefaults.colors(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Decimal
                            ),
                            isError = viewModel.updateDocState.value.error != null && viewModel.readingFront.doubleValue == 0.0,
                        )
                        if (viewModel.updateDocState.value.error != null && viewModel.readingFront.doubleValue == 0.0) {
                            Text(
                                text = "tidak boleh bernilai 0.0",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            //Nilai Pembacaan Batu Timbangan di Posisi Belakang & Kiri
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(.5f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Nilai Pembacaan Batu Timbangan di Posisi Belakang",
                            style = MaterialTheme.typography.labelMedium
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = if (viewModel.readingBack.doubleValue == 0.0) "" else viewModel.readingBack.doubleValue.toString(), // Tampilkan string kosong jika nilai adalah 0.0
                            onValueChange = {
                                viewModel.setReadingBack(it)
                            },
                            colors = TextFieldDefaults.colors(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Decimal
                            ),
                            isError = viewModel.updateDocState.value.error != null && viewModel.readingBack.doubleValue == 0.0,
                        )
                        if (viewModel.updateDocState.value.error != null && viewModel.readingBack.doubleValue == 0.0) {
                            Text(
                                text = "tidak boleh bernilai 0.0",
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
                            text = "Nilai Pembacaan Batu Timbangan di Posisi Kiri",
                            style = MaterialTheme.typography.labelMedium
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = if (viewModel.readingLeft.doubleValue == 0.0) "" else viewModel.readingLeft.doubleValue.toString(), // Tampilkan string kosong jika nilai adalah 0.0
                            onValueChange = {
                                viewModel.setReadingLeft(it)
                            },
                            colors = TextFieldDefaults.colors(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Decimal
                            ),
                            isError = viewModel.updateDocState.value.error != null && viewModel.readingLeft.doubleValue == 0.0,
                        )
                        if (viewModel.updateDocState.value.error != null && viewModel.readingLeft.doubleValue == 0.0) {
                            Text(
                                text = "tidak boleh bernilai 0.0",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            //Nilai Pembacaan Batu Timbangan di Posisi Kanan & Maksimal Total Pembacaan Batu Timbangan
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(.5f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Nilai Pembacaan Batu Timbangan di Posisi Kanan",
                            style = MaterialTheme.typography.labelMedium
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = if (viewModel.readingRight.doubleValue == 0.0) "" else viewModel.readingRight.doubleValue.toString(), // Tampilkan string kosong jika nilai adalah 0.0
                            onValueChange = {
                                viewModel.setReadingRight(it)
                            },
                            colors = TextFieldDefaults.colors(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Decimal
                            ),
                            isError = viewModel.updateDocState.value.error != null && viewModel.readingRight.doubleValue == 0.0,
                        )
                        if (viewModel.updateDocState.value.error != null && viewModel.readingRight.doubleValue == 0.0) {
                            Text(
                                text = "tidak boleh bernilai 0.0",
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
                            text = "Maksimal Total Pembacaan Batu Timbangan",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .clickable(enabled = false, onClick = {})
                        ) {
                            Text(
                                text = viewModel.maxTotalReading.doubleValue.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                        }
                    }
                }
            }
            //Ketarangan Hasil Kalibrasi
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Ketarangan Hasil Kalibrasi",
                        style = MaterialTheme.typography.labelMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewModel.resultCalibration.value.text,
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
                        isError = updateDocKalibrasiState.error != null,
                    )
                    if (updateDocKalibrasiState.error != null) {
                        Text(
                            text = updateDocKalibrasiState.error,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            repeat(10) {
                item {
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