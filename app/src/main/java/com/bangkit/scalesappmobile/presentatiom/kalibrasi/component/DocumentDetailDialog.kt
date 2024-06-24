package com.bangkit.scalesappmobile.presentatiom.kalibrasi.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentDetailDialog(
    document: AllForm,
    statusApproval: ApprovalStatus,
    onDismissRequest: () -> Unit,
) {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        openBottomSheet = true
    }

    // Sheet content
    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                openBottomSheet = false
                onDismissRequest()
            },
            sheetState = bottomSheetState,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Medium,
                        fontFamily = fontFamily
                    ),
                    text = "IDENTITAS ALAT"
                )  // Display some document details
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        scope.launch { bottomSheetState.hide() }
                            .invokeOnCompletion {
                                if (!bottomSheetState.isVisible) {
                                    openBottomSheet = false
                                    onDismissRequest()
                                }
                            }
                    }
                ) {
                    Image(
                        painter = painterResource(id = statusApproval.icon),
                        contentDescription = null
                    )
                }
            }
            Card(
                modifier = Modifier
                    .padding(16.dp),
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
                    Column(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.nomor_alat),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Nomor Alat",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ),
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = ":", style = TextStyle(fontFamily = fontFamily))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = document.scale.measuringEquipmentIdNumber,
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fontFamily
                                ),
                            )
                        }
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.scales),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Nama Alat",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ),
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = ":", style = TextStyle(fontFamily = fontFamily))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = document.scale.name,
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fontFamily
                                ),
                            )
                        }
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.scales),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Merk Pabrik / Tipe",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ),
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = ":", style = TextStyle(fontFamily = fontFamily))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = document.scale.brand + " / " + document.scale.kindType,
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fontFamily
                                ),
                            )
                        }
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.serial_number),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Nomor Seri",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ),
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = ":", style = TextStyle(fontFamily = fontFamily))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = document.scale.serialNumber,
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fontFamily
                                ),
                            )
                        }
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.weight),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Kapasitas",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ),
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = ":", style = TextStyle(fontFamily = fontFamily))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = document.scale.rangeCapacity.toString() + " " + document.scale.unit,
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fontFamily
                                ),
                            )
                        }
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 0.8.dp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(start = 16.dp),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily
                ),
                text = "IDENTITAS PEMILIK"
            )

            // Display some document details


            repeat(100) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}