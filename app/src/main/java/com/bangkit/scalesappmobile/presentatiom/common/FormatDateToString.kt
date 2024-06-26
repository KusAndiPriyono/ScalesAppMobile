package com.bangkit.scalesappmobile.presentatiom.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormatStringToDate(
    modifier: Modifier = Modifier,
    dateString: String,
) {
    val date = ZonedDateTime.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    val formattedDate = date.format(formatter)
    Text(
        modifier = modifier.padding(4.dp),
        text = formattedDate,
        style = MaterialTheme.typography.bodyMedium,
        fontFamily = fontFamily
    )
}