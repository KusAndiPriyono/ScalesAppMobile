package com.bangkit.scalesappmobile.presentatiom.kalibrasi.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.bangkit.scalesappmobile.util.formatDate

@Composable
fun ScaleCard(context: Context, document: AllForm) {
    Card(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp)
                    .align(Alignment.CenterVertically),
                model = ImageRequest.Builder(context).crossfade(true)
                    .data(document.scale.imageCover)
                    .build(),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(12.dp))
            VerticalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = Color.Gray
            )
            Column(modifier = Modifier.padding(8.dp)) {
                DocumentDetailsRow(
                    R.drawable.nomor_alat,
                    "Nomor Alat",
                    document.scale.measuringEquipmentIdNumber
                )
                DocumentDetailsRow(R.drawable.scales, "Nama Alat", document.scale.name)
                DocumentDetailsRow(
                    R.drawable.scales,
                    "Merk Pabrik / Tipe",
                    "${document.scale.brand} / ${document.scale.kindType}"
                )
                DocumentDetailsRow(
                    R.drawable.serial_number,
                    "Nomor Seri",
                    document.scale.serialNumber
                )
                DocumentDetailsRow(
                    R.drawable.weight,
                    "Kapasitas",
                    "${document.scale.rangeCapacity} ${document.scale.unit}"
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        modifier = Modifier.padding(start = 16.dp),
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamily
        ),
        text = title
    )
}

@Composable
fun DetailsBox(document: AllForm) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val details = listOf(
                DocumentDetail(
                    R.drawable.pemilik,
                    "Nama Pemilik",
                    document.scale.parentMachineOfEquipment
                ),
                DocumentDetail(R.drawable.method, "Metoda Kalibrasi", document.calibrationMethod),
                DocumentDetail(R.drawable.nomor_alat, "Acuan Standard", document.reference),
                DocumentDetail(
                    R.drawable.nomor_alat,
                    "Standar Kalibrasi",
                    document.standardCalibration
                ),
                DocumentDetail(
                    R.drawable.tgl_kalibrasi,
                    "Tanggal Kalibrasi",
                    formatDate(date = document.createdAt.toString())
                ),
                DocumentDetail(R.drawable.location, "Tempat Kalibrasi", document.scale.location),
                DocumentDetail(R.drawable.temperature, "Suhu", "${document.suhu}°C"),
                DocumentDetail(R.drawable.note, "Hasil Kalibrasi", document.resultCalibration),
                DocumentDetail(
                    R.drawable.next_kalibrasi,
                    "Berlaku Sampai",
                    formatDate(date = document.validUntil.toString())
                ),
            )

            details.forEach { detail ->
                DocumentDetailsRow(detail.icon, detail.label, detail.value)
                HorizontalDivider(
                    modifier = Modifier.padding(4.dp),
                    thickness = 0.8.dp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun DetailsHasilKalibrasi(
    document: AllForm,
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val details = listOf(
                DocumentDetail(
                    R.drawable.weight_scale,
                    "Posisi anak timbangan di tengah",
                    document.readingCenter.toString()
                ),
                DocumentDetail(
                    R.drawable.weight_scale,
                    "Posisi anak timbangan di depan",
                    document.readingFront.toString()
                ),
                DocumentDetail(
                    R.drawable.weight_scale,
                    "Posisi anak timbangan di belakang",
                    document.readingBack.toString()
                ),
                DocumentDetail(
                    R.drawable.weight_scale,
                    "Posisi anak timbangan di kiri",
                    document.readingLeft.toString()
                ),
                DocumentDetail(
                    R.drawable.weight_scale,
                    "Posisi anak timbangan di kanan",
                    document.readingRight.toString()
                ),
                DocumentDetail(
                    R.drawable.weight_scale,
                    "Total",
                    document.maxTotalReading.toString()
                ),
            )

            details.forEach { detail ->
                DocumentDetailsRow(detail.icon, detail.label, detail.value)
                HorizontalDivider(
                    modifier = Modifier.padding(4.dp),
                    thickness = 0.8.dp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun DocumentDetailsRow(icon: Int, label: String, value: String) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
    ) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = icon),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Thin,
                fontFamily = fontFamily,
                color = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = ":", style = TextStyle(fontFamily = fontFamily))
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily
            ),
        )
    }
}

data class DocumentDetail(val icon: Int, val label: String, val value: String)