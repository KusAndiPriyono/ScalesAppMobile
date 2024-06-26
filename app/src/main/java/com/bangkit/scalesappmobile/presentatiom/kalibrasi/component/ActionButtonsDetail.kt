package com.bangkit.scalesappmobile.presentatiom.kalibrasi.component

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.presentatiom.common.DisplayAlertDialog
import com.bangkit.scalesappmobile.util.createPdfFile
import kotlinx.coroutines.launch

@Composable
fun ActionButtonsDetails(
    context: Context,
    document: AllForm,
    statusApproval: ApprovalStatus,
    isLoading: Boolean,
    setLoading: (Boolean) -> Unit,
    onClickDeleteDocument: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    var isDialogDeleteDocumentOpened by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ElevatedButton(
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
            Text(text = "Edit")
        }

        if (statusApproval == ApprovalStatus.fromString("Approved")) {
            ElevatedButton(
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                onClick = {
                    setLoading(true)
                    scope.launch {
                        val pdfFile = createPdfFile(context, document)
                        setLoading(false)
                        pdfFile?.let {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                setDataAndType(
                                    FileProvider.getUriForFile(
                                        context,
                                        context.packageName + ".provider",
                                        it
                                    ),
                                    "application/pdf"
                                )
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            context.startActivity(intent)
                        } ?: run {
                            Toast.makeText(context, "Failed to create PDF", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Print,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background
                    )
                    Text(text = "Print PDF")
                }
            }
        }

        ElevatedButton(
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            onClick = {
                isDialogDeleteDocumentOpened = true
            }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
            Text(text = "Delete")
        }
    }

    DisplayAlertDialog(
        title = "Hapus Timbangan",
        message = "Apakah Anda yakin ingin menghapus Document ini?",
        dialogOpened = isDialogDeleteDocumentOpened,
        onDialogClosed = {
            isDialogDeleteDocumentOpened = false
        },
        onYesClicked = {
            onClickDeleteDocument()
            isDialogDeleteDocumentOpened = false
        }
    )
}
