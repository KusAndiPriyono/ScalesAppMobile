package com.bangkit.scalesappmobile.presentatiom.createscales.component

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun CalibrationDateTextField(
    modifier: Modifier = Modifier,
    calibrationDate: TextFieldState,
    onCurrentCalibrationDateChange: (String) -> Unit,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

//    val currentDate = Date().toFormattedString()
//    var selectedDate by rememberSaveable { mutableStateOf(currentDate) }

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
            onCurrentCalibrationDateChange(formattedDate)
        }, year, month, day)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.end_date),
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            readOnly = true,
            value = calibrationDate.text,
            onValueChange = { },
            trailingIcon = { Icons.Default.DateRange },
            interactionSource = interactionSource
        )
    }

    if (isPressed) {
        datePickerDialog.show()
    }
}

fun Int.toMonthName(): String {
    return DateFormatSymbols().months[this]
}

fun Date.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}