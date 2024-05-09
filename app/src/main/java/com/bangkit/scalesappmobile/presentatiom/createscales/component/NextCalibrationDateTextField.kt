package com.bangkit.scalesappmobile.presentatiom.createscales.component

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.bangkit.scalesappmobile.util.toFormattedString
import java.util.Calendar
import java.util.Date

@Composable
fun NextCalibrationDateTextField(
    nextCalibrationDate: TextFieldState,
    isError: Boolean,
    setScalesNextCalibrationDate: (String) -> Unit,
) {


    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()


    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog =
        DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val newDate = Calendar.getInstance()
            newDate.set(year, month, dayOfMonth)
            val formattedDate = newDate.time.toFormattedString()
            setScalesNextCalibrationDate(formattedDate)
        }, year, month, day)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.next_calibration_date),
            style = MaterialTheme.typography.labelMedium
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            colors = TextFieldDefaults.colors(),
            value = nextCalibrationDate.text,
            onValueChange = { },
            trailingIcon = { Icons.Default.DateRange },
            isError = isError,
            interactionSource = interactionSource
        )
    }

    if (isPressed) {
        datePickerDialog.show()
    }
}