package com.bangkit.scalesappmobile.presentatiom.kalibrasi.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import java.util.TimeZone


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DocumentHolder(
    document: AllForm,
    navigateToDetail: (AllForm) -> Unit,
) {
    var componentHeight by remember {
        mutableStateOf(0.dp)
    }
    val localDensity = LocalDensity.current

    Row(
        modifier = Modifier.clickable { navigateToDetail.toString() }
    ) {
        Spacer(modifier = Modifier.width(14.dp))
        Surface(
            modifier = Modifier
                .width(2.dp)
                .height(componentHeight + 14.dp),
            tonalElevation = 1.dp,
        ) {}
        Spacer(modifier = Modifier.width(14.dp))
        Surface(
            modifier = Modifier
                .clip(shape = Shapes().medium)
                .onGloballyPositioned {
                    componentHeight = with(localDensity) { it.size.height.toDp() }
                },
            tonalElevation = 1.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                DocumentHeader(
                    statusApproval = ApprovalStatus.fromString(document.approval),
                    time = document.createdAt.toInstant()
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.nomor_alat),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 7.dp),
                        text = ": " + document.scale.measuringEquipmentIdNumber,
                        style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                        fontFamily = fontFamily
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.scales),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 7.dp),
                        text = ": " + document.scale.kindType,
                        style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                        fontFamily = fontFamily
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.serial_number),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 7.dp),
                        text = ": " + document.scale.serialNumber,
                        style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                        fontFamily = fontFamily
                    )

                    Image(
                        modifier = Modifier
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.temperature),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 7.dp),
                        text = ": " + document.suhu.toString() + "°C",
                        style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                        fontFamily = fontFamily
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.note),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 7.dp),
                        text = ": " + document.resultCalibration,
                        style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                        fontFamily = fontFamily
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DocumentHeader(statusApproval: ApprovalStatus, time: Instant) {

    val dateFormat = remember {
        SimpleDateFormat("HH:mm", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
    val formattedTime = remember { dateFormat.format(Date.from(time)) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(statusApproval.containerColor)
            .padding(horizontal = 14.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = statusApproval.icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = statusApproval.name,
                color = statusApproval.contentColor,
                style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                fontFamily = fontFamily
            )
        }
        Text(
            text = formattedTime,
            color = statusApproval.contentColor,
            style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
            fontFamily = fontFamily
        )
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun DocumentHolderPreview() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        DocumentHolder(
//            document = AllForm(
//                id = "1",
//                approval = "Rejected",
//                createdAt = Date(),
//                calibrationMethod = "Calibration Method",
//                reference = "Reference",
//                resultCalibration = "Result Calibration",
//                scale = "Scale",
//                standardCalibration = "Standard Calibration",
//                suhu = 0,
//                validUntil = "Valid Until",
//                readingCenter = 0.0,
//                readingFront = 0.0,
//                readingBack = 0.0,
//                readingLeft = 0.0,
//                readingRight = 0.0,
//                maxTotalReading = 0.0
//            ),
//            navigateToDetail = {}
//        )
//    }
//}