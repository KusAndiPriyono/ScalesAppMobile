package com.bangkit.scalesappmobile.presentatiom.kalibrasi.component

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import java.time.LocalDate

@SuppressLint("DefaultLocale")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateHeader(
    localDate: LocalDate,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = String.format("%02d", localDate.dayOfMonth),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Light,
                    fontFamily = fontFamily
                )
            )
            Text(
                text = localDate.dayOfWeek.toString().take(3),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Light,
                    fontFamily = fontFamily
                )
            )
        }
        Spacer(modifier = Modifier.padding(14.dp))
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = localDate.month.toString().lowercase().replaceFirstChar { it.titlecase() },
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Light
                )
            )
            Text(
                text = "${localDate.year}",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Light,
                    fontFamily = fontFamily
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateHeaderPreview() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateHeader(LocalDate.now())
    }
}