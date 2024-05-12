package com.bangkit.scalesappmobile.presentatiom.home.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.ui.theme.fontFamily

@Composable
fun ScalesItem(
    scales: Scales,
    onClick: (() -> Unit)? = null,
) {

    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onClick?.invoke() },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .size(150.dp),
                model = ImageRequest.Builder(context).crossfade(true).data(scales.imageCover)
                    .build(),
                contentDescription = null,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
            ) {
                Text(
                    text = scales.brand,
                    style = MaterialTheme.typography.titleSmall,
                    fontFamily = fontFamily,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = scales.kindType,
                    style = MaterialTheme.typography.titleSmall,
                    fontFamily = fontFamily,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = scales.location,
                    style = MaterialTheme.typography.titleSmall,
                    fontFamily = fontFamily,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        stringResource(id = R.string.Status),
                        style = MaterialTheme.typography.titleSmall,
                        fontFamily = fontFamily,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = scales.status,
                        style = MaterialTheme.typography.titleSmall,
                        fontFamily = fontFamily,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ScalesItemPreview() {
    ScalesItem(
        scales = Scales(
            brand = "brand",
            calibrationDate = "calibrationDate",
            calibrationPeriod = 1,
            calibrationPeriodInYears = 1.1,
            equipmentDescription = "equipmentDescription",
            id = "id",
            imageCover = "https://www.themealdb.com/images/media/meals/sytuqu1511553755.jpg",
            kindType = "kindType",
            location = "location",
            measuringEquipmentIdNumber = "measuringEquipmentIdNumber",
            name = "name",
            nextCalibrationDate = "nextCalibrationDate",
            parentMachineOfEquipment = "parentMachineOfEquipment",
            rangeCapacity = 1,
            ratingsAverage = 1.4,
            ratingsQuantity = 1,
            serialNumber = "serialNumber",
            slug = "slug",
            status = "status",
            unit = "unit",
            v = 1
        )
    )
}