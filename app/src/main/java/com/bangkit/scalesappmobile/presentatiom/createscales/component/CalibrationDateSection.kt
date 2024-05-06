package com.bangkit.scalesappmobile.presentatiom.createscales.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalibrationDateSection(
    calibrationDate: TextFieldState,
    nextCalibrationDate: TextFieldState,
    updateDateTime: (ZonedDateTime) -> Unit,
    onCurrentCalibrationDateChange: (String) -> Unit,
    onCurrentNextCalibrationDateChange: (String) -> Unit,
    dateDialog: UseCaseState,
    timeDialog: UseCaseState,
) {
    var currentDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var currentTime by remember {
        mutableStateOf(LocalTime.now())
    }
    var dateTimeUpdated by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = calibrationDate.text,
            onValueChange = { onCurrentCalibrationDateChange(it) },
            label = { Text(text = "Tanggal Kalibrasi") },
            readOnly = true,
            trailingIcon = {
                DateTimeSelectionIcon(
                    dateTimeUpdated = dateTimeUpdated,
                    currentDate = currentDate,
                    currentTime = currentTime,
                    updateDateTime = updateDateTime,
                    dateDialog = dateDialog,
                    timeDialog = timeDialog
                )
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = nextCalibrationDate.text,
            onValueChange = { onCurrentNextCalibrationDateChange(it) },
            label = { Text(text = "Kalibrasi Selanjutnya") },
            readOnly = true,
            trailingIcon = {
                DateTimeSelectionIcon(
                    dateTimeUpdated = dateTimeUpdated,
                    currentDate = currentDate,
                    currentTime = currentTime,
                    updateDateTime = updateDateTime,
                    dateDialog = dateDialog,
                    timeDialog = timeDialog
                )
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
                updateDateTime(
                    ZonedDateTime.of(
                        currentDate,
                        currentTime,
                        ZoneId.systemDefault()
                    )
                )
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DateTimeSelectionIcon(
    dateTimeUpdated: Boolean,
    currentDate: LocalDate,
    currentTime: LocalTime,
    updateDateTime: (ZonedDateTime) -> Unit,
    dateDialog: UseCaseState,
    timeDialog: UseCaseState,
) {

    if (dateTimeUpdated) {
        IconButton(
            onClick = {
                updateDateTime(
                    ZonedDateTime.of(
                        currentDate,
                        currentTime,
                        ZoneId.systemDefault()
                    )
                )
            }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Icon",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    } else {
        IconButton(
            onClick = {
                dateDialog.show()
                timeDialog.show()
            }
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date Range Icon",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}