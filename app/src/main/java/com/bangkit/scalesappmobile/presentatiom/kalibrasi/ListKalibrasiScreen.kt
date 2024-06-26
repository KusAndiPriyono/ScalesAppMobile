package com.bangkit.scalesappmobile.presentatiom.kalibrasi

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.presentatiom.common.EmptyPage
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.component.ApprovalStatus
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.component.DocumentDetailDialog
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.component.DocumentHolder
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.state.DocumentState
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.ramcosta.composedestinations.annotation.Destination
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun ListKalibrasiScreen(
    navigator: KalibrasiNavigator,
    viewModel: ListKalibrasiViewModel = hiltViewModel(),
) {

    val documentState = viewModel.documentState.value
    val selectedStatusApproval = viewModel.selectedStatusApproval.collectAsState().value

    val filteredDocuments = if (selectedStatusApproval == "All") {
        documentState.documents
    } else {
        documentState.documents.mapValues { entry ->
            entry.value.filter { it.approval == selectedStatusApproval }
        }.filterValues { it.isNotEmpty() }
    }

    var selectedDocument by remember {
        mutableStateOf<AllForm?>(null)
    }

    ListKalibrasiScreenContent(
        documentKalibrasi = documentState.copy(documents = filteredDocuments),
        navigateToDetail = { document ->
            selectedDocument =
                documentState.documents.values.flatten().find { it.id == document }
        },
        onSelectedStatusApproval = { viewModel.setSelectedStatusApproval(it) },
        selectedStatusApproval = selectedStatusApproval,
        isLoading = documentState.isLoading
    )

    selectedDocument?.let { document ->
        DocumentDetailDialog(
            document = document,
            onDismissRequest = {
                selectedDocument = null
            },
            statusApproval = ApprovalStatus.fromString(document.approval),
            onClickDeleteDocument = {
                viewModel.deleteDocumentKalibrasi(document.id)
                navigator.openKalibrasi()
                selectedDocument = null
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ListKalibrasiScreenContent(
    documentKalibrasi: DocumentState,
    navigateToDetail: (String) -> Unit,
    onSelectedStatusApproval: (String) -> Unit,
    selectedStatusApproval: String,
    isLoading: Boolean,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Daftar List Form Kalibrasi", fontSize = 18.sp)
                }
            )
        },
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LoadingStateComponent()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .navigationBarsPadding()
                    .padding(top = paddingValues.calculateTopPadding())
            ) {
                ApprovalSelection(
                    approval = documentKalibrasi.documents.values.flatten(),
                    onClick = onSelectedStatusApproval,
                    selectedApproval = selectedStatusApproval
                )
                if (documentKalibrasi.documents.isNotEmpty()) {
                    LazyColumn {
                        documentKalibrasi.documents.forEach { (localDate, forms) ->
                            stickyHeader(key = localDate) {
                                DateHeader(localDate = localDate)
                            }
                            items(forms) { form ->
                                DocumentHolder(
                                    document = form,
                                    navigateToDetail = { navigateToDetail(form.id) }
                                )
                            }
                            item {
                                HorizontalDivider(
                                    modifier = Modifier.padding(start = 24.dp),
                                    thickness = 0.8.dp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                } else {
                    EmptyPage()
                }
            }
        }
    }
}

@Composable
fun ApprovalSelection(
    approval: List<AllForm>,
    onClick: (String) -> Unit,
    selectedApproval: String,
) {
    val uniqueApprovals = listOf("All") + approval.map { it.approval }.distinct()
    LazyRow(

        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(uniqueApprovals) { approvalStatus ->
            ApprovalItem(
                approval = approvalStatus,
                onClick = {
                    onClick(approvalStatus)
                }, selectedApproval = selectedApproval
            )
        }
    }
}

@Composable
fun ApprovalItem(
    approval: String,
    selectedApproval: String,
    onClick: () -> Unit,
) {
    val selected = approval == selectedApproval
    Card(
        modifier = Modifier
            .width(100.dp)
            .wrapContentHeight()
            .clickable {
                onClick()
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = approval,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (selected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

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