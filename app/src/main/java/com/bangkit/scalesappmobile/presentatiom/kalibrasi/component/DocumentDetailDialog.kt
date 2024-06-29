package com.bangkit.scalesappmobile.presentatiom.kalibrasi.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
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
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.presentatiom.home.component.UserRole
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentDetailDialog(
    document: AllForm,
    statusApproval: ApprovalStatus,
    userRole: UserRole,
    onDismissRequest: () -> Unit,
    onClickDeleteDocument: () -> Unit,
    onClickEditDocument: (AllForm) -> Unit,
) {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                    )
                    // Display some document details
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
                ScaleCard(context = context, document = document)
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 0.8.dp,
                    color = Color.Gray
                )
                // Display some document details
                Spacer(modifier = Modifier.height(8.dp))
                SectionTitle("IDENTITAS PEMILIK")
                DetailsBox(document = document)
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 0.8.dp,
                    color = Color.Gray
                )
                //Display some results of calibration
                Spacer(modifier = Modifier.height(8.dp))
                SectionTitle("HASIL KALIBRASI")
                DetailsHasilKalibrasi(document = document)
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 0.8.dp,
                    color = Color.Gray
                )
                //Create PDF
                Spacer(modifier = Modifier.height(8.dp))
                ActionButtonsDetails(
                    context = context,
                    document = document,
                    statusApproval = statusApproval,
                    onClickDeleteDocument = {
                        onClickDeleteDocument()
                    },
                    onClickEditDocument = {
                        onClickEditDocument(it)
                    },
                    userRole = userRole
                )
                repeat(10) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
