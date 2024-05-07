package com.bangkit.scalesappmobile.presentatiom.createscales.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BrandScalesTextField(
    modifier: Modifier = Modifier,
    brand: String,
    onCurrentBrandChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = brand,
        onValueChange = { onCurrentBrandChange(it) },
        label = {
            Text(
                text = "Merk",
            )
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Email,
            autoCorrect = true,
        )
    )
}

@Composable
fun EquipmentDescriptionTextField(
    modifier: Modifier = Modifier,
    equipmentDescription: String,
    onCurrentEquipmentDescriptionChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = equipmentDescription,
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

@Composable
fun KindTypeTextField(
    modifier: Modifier = Modifier,
    kindType: String,
    onCurrentKindTypeChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = kindType,
        onValueChange = { onCurrentKindTypeChange(it) },
        label = {
            Text(text = "Tipe Alat")
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Email,
            autoCorrect = true
        )
    )
}

@Composable
fun LocationScalesTextField(
    modifier: Modifier = Modifier,
    location: String,
    onCurrentLocationChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = location,
        onValueChange = { onCurrentLocationChange(it) },
        label = {
            Text(text = "Lokasi Alat")
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Email,
            autoCorrect = true
        )
    )
}

@Composable
fun NameScalesTextField(
    modifier: Modifier = Modifier,
    name: String,
    onCurrentNameChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = name,
        onValueChange = { onCurrentNameChange(it) },
        label = {
            Text(text = "Nama Jenis Timbangan")
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Email,
            autoCorrect = true
        )
    )
}

@Composable
fun MachineOfEquipmentTextField(
    modifier: Modifier = Modifier,
    parentMachineOfEquipment: String,
    onCurrentParentMachineOfEquipmentChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = parentMachineOfEquipment,
        onValueChange = { onCurrentParentMachineOfEquipmentChange(it) },
        label = {
            Text(text = "Mesin Induk Alat")
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Email,
            autoCorrect = true
        )
    )
}

@Composable
fun SerialNumberTextField(
    modifier: Modifier = Modifier,
    serialNumber: String,
    onCurrentSerialNumberChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = serialNumber,
        onValueChange = { onCurrentSerialNumberChange(it) },
        label = {
            Text(text = "Nomor Seri Alat")
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Email,
            autoCorrect = true
        )
    )
}

@Composable
fun UnitScalesTextField(
    modifier: Modifier = Modifier,
    unit: String,
    onCurrentUnitChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = unit,
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
        Text(
            text = "Note: Isi dengan satuan kg atau g",
            style = TextStyle(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )
    }
}

@Composable
fun CalibrationPeriodTextField(
    modifier: Modifier = Modifier,
    calibrationPeriod: Int,
    onCurrentCalibrationPeriodChange: (Int) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = calibrationPeriod.toString(),
        onValueChange = { newValue ->
            onCurrentCalibrationPeriodChange(newValue.toIntOrNull() ?: 0)
        },
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

@Composable
fun RangeCapacityTextField(
    modifier: Modifier = Modifier,
    rangeCapacity: Int,
    onCurrentRangeCapacityChange: (Int) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = rangeCapacity.toString(),
        onValueChange = { newValue ->
            onCurrentRangeCapacityChange(newValue.toIntOrNull() ?: 0)
        },
        label = {
            Text(text = "Kapasitas Timbangan")
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Number,
            autoCorrect = true
        )
    )
}