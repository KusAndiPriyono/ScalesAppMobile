package com.bangkit.scalesappmobile.presentatiom.kalibrasi

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.presentatiom.common.EmptyPage
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.component.ApprovalSelection
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.component.ApprovalStatus
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.component.DateHeader
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.component.DocumentDetailDialog
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.component.DocumentHolder
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.state.DocumentState
import com.ramcosta.composedestinations.annotation.Destination

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
            },
            onClickEditDocument = {
                navigator.openUpdateDocKalibrasi(id = document.id, allForm = document)
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
        if (documentKalibrasi.isLoading) {
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



