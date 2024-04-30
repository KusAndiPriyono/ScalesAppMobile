package com.bangkit.scalesappmobile.presentatiom.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.domain.model.Scales

@Composable
fun ScalesItem(
    modifier: Modifier = Modifier, scales: Scales, onClick: (() -> Unit)? = null
) {

    val context = LocalContext.current
    Row(
        modifier = modifier.clickable { onClick?.invoke() },

        ) {
//        AsyncImage(
//            modifier = Modifier
//                .size(150.dp)
//                .clip(MaterialTheme.shapes.medium),
//            model = ImageRequest.Builder(context).data(scales.imageCover).crossfade(true).build(),
//            contentDescription = null,
////            contentScale = ContentScale.Crop
//        )
        Image(
            modifier = Modifier
                .size(150.dp)
                .clip(MaterialTheme.shapes.medium),
            contentDescription = scales.name,
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(scales.imageCover)
                    .apply(block = fun ImageRequest.Builder.() {
                        placeholder(R.drawable.logo)
                    }).build()
            )
        )
        Column(
//            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = 3.dp)
                .height(150.dp)
        ) {
            Text(
                text = scales.equipmentDescription,
                style = MaterialTheme.typography.bodyMedium.copy(),
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = scales.brand,
                style = MaterialTheme.typography.bodyMedium.copy(),
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(3.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = scales.location,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(11.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = scales.nextCalibrationDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScalesItemPreview() {
    ScalesItem(
        scales = Scales(
            brand = "brand",
            calibrationDate = "calibrationDate",
            calibrationPeriod = 1,
            calibrationPeriodInYears = 1,
            equipmentDescription = "equipmentDescription",
            id = "id",
            imageCover = "imageCover",
            kindType = "kindType",
            location = "location",
            measuringEquipmentIdNumber = "measuringEquipmentIdNumber",
            name = "name",
            nextCalibrationDate = "nextCalibrationDate",
            parentMachineOfEquipment = "parentMachineOfEquipment",
            rangeCapacity = 1,
            ratingsAverage = 1,
            ratingsQuantity = 1,
            serialNumber = "serialNumber",
            slug = "slug",
            status = "status",
            unit = "unit",
            v = 1
        )
    )
}