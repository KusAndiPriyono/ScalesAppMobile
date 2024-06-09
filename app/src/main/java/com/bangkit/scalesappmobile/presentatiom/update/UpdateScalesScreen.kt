package com.bangkit.scalesappmobile.presentatiom.update

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.presentatiom.createscales.component.BloomDropDown
import com.bangkit.scalesappmobile.presentatiom.createscales.state.DateType
import com.bangkit.scalesappmobile.presentatiom.home.HomeNavigator
import com.bangkit.scalesappmobile.presentatiom.home.component.StandardToolbar
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.bangkit.scalesappmobile.util.UiEvents
import com.bangkit.scalesappmobile.util.compressImage
import com.bangkit.scalesappmobile.util.saveImage
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.mr0xf00.easycrop.CropError
import com.mr0xf00.easycrop.CropResult
import com.mr0xf00.easycrop.crop
import com.mr0xf00.easycrop.rememberImageCropper
import com.mr0xf00.easycrop.ui.ImageCropperDialog
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun UpdateScalesScreen(
    id: String?,
    scalesDetails: ScalesDetails?,
    navigator: HomeNavigator,
    viewModel: UpdateViewModel = hiltViewModel(),
) {
    val updateScalesState = viewModel.updateScales.value
    val scaffoldState = rememberBottomSheetScaffoldState()

    val context = LocalContext.current

    val imageCropper = rememberImageCropper()
    val scope = rememberCoroutineScope()
    var imageUri by remember { mutableStateOf<File?>(null) }
    var compressedImageUri by remember { mutableStateOf<Uri?>(null) }

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

    var cameraPermissionState by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                cameraPermissionState = granted
            }
        )

    @Suppress("DEPRECATION")
    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                if (imageUri != null) {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        context.contentResolver,
                        imageUri!!.toUri()
                    )
                    val compressedBitmap = compressImage(bitmap)
                    compressedImageUri = saveImage(context, compressedBitmap)


                    scope.launch {
                        when (imageCropper.crop(uri = imageUri!!.toUri(), context = context)) {
                            CropError.LoadingError -> {
                                Toast.makeText(
                                    context,
                                    "CropError.LoadingError",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            CropError.SavingError -> {
                                Toast.makeText(
                                    context,
                                    "CropError.SavingError",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                            CropResult.Cancelled -> {
                                Toast.makeText(
                                    context,
                                    "CropResult.Cancelled",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                            is CropResult.Success -> {
                                viewModel.setScalesImageCover(
                                    compressedImageUri
                                )
                            }
                        }
                    }
                }
            }
        }
    )

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                scope.launch {
                    when (val result = imageCropper.crop(uri = uri, context = context)) {
                        CropError.LoadingError -> {
                            Toast.makeText(context, "CropError.LoadingError", Toast.LENGTH_SHORT)
                                .show()
                        }

                        CropError.SavingError -> {
                            Toast.makeText(context, "CropError.SavingError", Toast.LENGTH_SHORT)
                                .show()
                        }

                        CropResult.Cancelled -> {
                            Toast.makeText(context, "CropResult.Cancelled", Toast.LENGTH_SHORT)
                                .show()
                        }

                        is CropResult.Success -> {
                            viewModel.setScalesImageCover(
                                saveImage(
                                    context = context,
                                    bitmap = result.bitmap.asAndroidBitmap()
                                )
                            )
                        }
                    }
                }
            }
        }

    LaunchedEffect(key1 = true, block = {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    })

    LaunchedEffect(key1 = true, block = {
        if (id != null && scalesDetails != null) {
            viewModel.loadScalesData(scalesDetails)
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
                    navigator.openScalesDetails(id)
                }
            }
        }
    }

    val cropState = imageCropper.cropState
    if (cropState != null) ImageCropperDialog(state = cropState)

    Scaffold(
        topBar = {
            StandardToolbar(
                navigate = {
                    navigator.popBackStack()
                },
                title = {
                    Text(text = "Edit Scales", fontSize = 18.sp)
                },
                showBackArrow = true,
                navActions = {
                    SaveTextButtonContent(
                        isLoading = viewModel.updateScales.value.isLoading,
                        onClick = {
                            viewModel.saveScalesData(id!!)
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

            //Nama Timbangan
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Nama Timbangan",
                        style = MaterialTheme.typography.labelMedium
                    )
                    BloomDropDown(
                        modifier = Modifier.fillMaxWidth(),
                        options = viewModel.scalesNames,
                        selectedOption = viewModel.scalesName.value.text,
                        onOptionSelected = { item ->
                            viewModel.setScalesName(item)
                        }
                    )
                    if (updateScalesState.error != null) {
                        Text(
                            text = updateScalesState.error,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            //Merk/Pabrik dan Type
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
                            text = "Merk/Pabrik",
                            style = MaterialTheme.typography.labelMedium
                        )
                        BloomDropDown(
                            modifier = Modifier.fillMaxWidth(),
                            options = viewModel.brands,
                            selectedOption = viewModel.scalesBrand.value.text,
                            onOptionSelected = { item ->
                                viewModel.setScalesBrand(item)
                            }
                        )

                        if (updateScalesState.error != null) {
                            Text(
                                text = updateScalesState.error,
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
                            text = "Type",
                            style = MaterialTheme.typography.labelMedium
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.scalesKindType.value.text,
                            onValueChange = {
                                viewModel.setScalesKindType(it)
                            },
                            colors = TextFieldDefaults.colors(),
                            placeholder = {
                                Text(
                                    text = "Type",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                            ),
                            isError = updateScalesState.error != null
                        )

                        if (updateScalesState.error != null) {
                            Text(
                                text = updateScalesState.error,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            //Kapasitas Timbangan dan Satuan
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
                            text = "Kapasitas Timbangan",
                            style = MaterialTheme.typography.labelMedium
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.scalesRangeCapacity.value.toString(),
                            onValueChange = {
                                viewModel.setScalesRangeCapacity(it.toInt())
                            },
                            colors = TextFieldDefaults.colors(),
                            placeholder = {
                                Text(
                                    text = "Kapasitas Timbangan",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                            ),
                            isError = updateScalesState.error != null
                        )

                        if (updateScalesState.error != null) {
                            Text(
                                text = updateScalesState.error,
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
                            text = "Satuan",
                            style = MaterialTheme.typography.labelMedium
                        )

                        BloomDropDown(
                            modifier = Modifier.fillMaxWidth(),
                            options = viewModel.units,
                            selectedOption = viewModel.scalesUnit.value.text,
                            onOptionSelected = { item ->
                                viewModel.setScalesUnit(item)
                            }
                        )

                        if (updateScalesState.error != null) {
                            Text(
                                text = updateScalesState.error,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            //Serial number dan lokasi timbangan
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
                            text = "Serial Number",
                            style = MaterialTheme.typography.labelMedium
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.scalesSerialNumber.value.text,
                            onValueChange = {
                                viewModel.setScalesSerialNumber(it)
                            },
                            colors = TextFieldDefaults.colors(),
                            placeholder = {
                                Text(
                                    text = "Serial Number",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                            ),
                            isError = updateScalesState.error != null
                        )

                        if (updateScalesState.error != null) {
                            Text(
                                text = updateScalesState.error,
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
                            text = "Lokasi Timbangan",
                            style = MaterialTheme.typography.labelMedium
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.scalesLocation.value.text,
                            onValueChange = {
                                viewModel.setScalesLocation(it)
                            },
                            colors = TextFieldDefaults.colors(),
                            placeholder = {
                                Text(
                                    text = "Lokasi Timbangan",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                            ),
                            isError = updateScalesState.error != null
                        )

                        if (updateScalesState.error != null) {
                            Text(
                                text = updateScalesState.error,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            //Periode Kalibrasi dan Document Number
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(.3f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Periode Kalibrasi",
                            style = MaterialTheme.typography.labelMedium
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.scalesCalibrationPeriod.value.toString(),
                            onValueChange = {
                                viewModel.setScalesCalibrationPeriod(it.toInt())
                            },
                            colors = TextFieldDefaults.colors(),
                            placeholder = {
                                Text(
                                    text = "Periode Kalibrasi",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                            ),
                            isError = updateScalesState.error != null
                        )

                        if (updateScalesState.error != null) {
                            Text(
                                text = updateScalesState.error,
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
                            text = "Document Number",
                            style = MaterialTheme.typography.labelMedium
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.scalesDocNumber.value.text,
                            onValueChange = {
                                viewModel.setScalesDocNumber(it)
                            },
                            colors = TextFieldDefaults.colors(),
                            placeholder = {
                                Text(
                                    text = "Document Number",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                            ),
                            isError = updateScalesState.error != null
                        )

                        if (updateScalesState.error != null) {
                            Text(
                                text = updateScalesState.error,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            //Tanggal Kalibrasi dan tanggal kalibrasi selanjutnya
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
                            text = "Tanggal Kalibrasi",
                            style = MaterialTheme.typography.labelMedium
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.scalesCalibrationDate.value.text,
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
                            isError = updateScalesState.error != null
                        )

                        if (updateScalesState.error != null) {
                            Text(
                                text = updateScalesState.error,
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
                            text = "Tanggal Kalibrasi Selanjutnya",
                            style = MaterialTheme.typography.labelMedium
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.scalesNextCalibrationDate.value.text,
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
                            isError = updateScalesState.error != null
                        )

                        if (updateScalesState.error != null) {
                            Text(
                                text = updateScalesState.error,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            //Description dan Mesin Induk Alat
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
                            text = "Description",
                            style = MaterialTheme.typography.labelMedium
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.scalesEquipmentDescription.value.text,
                            onValueChange = {
                                viewModel.setScalesEquipmentDescription(it)
                            },
                            colors = TextFieldDefaults.colors(),
                            placeholder = {
                                Text(
                                    text = "Description",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                            ),
                            isError = updateScalesState.error != null
                        )

                        if (updateScalesState.error != null) {
                            Text(
                                text = updateScalesState.error,
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
                            text = "Mesin Induk Alat",
                            style = MaterialTheme.typography.labelMedium
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.scalesParentMachineOfEquipment.value.text,
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
                            ),
                            isError = updateScalesState.error != null
                        )

                        if (updateScalesState.error != null) {
                            Text(
                                text = updateScalesState.error,
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