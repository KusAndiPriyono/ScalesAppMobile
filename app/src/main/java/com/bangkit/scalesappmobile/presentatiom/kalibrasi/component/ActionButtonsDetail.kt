package com.bangkit.scalesappmobile.presentatiom.kalibrasi.component

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.util.createPdfFile
import kotlinx.coroutines.launch

@Composable
fun ActionButtonsDetails(
    context: Context,
    document: AllForm,
    statusApproval: ApprovalStatus,
    isLoading: Boolean,
    setLoading: (Boolean) -> Unit,
) {
    val scope = rememberCoroutineScope()
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
            Spacer(modifier = Modifier.width(8.dp))
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
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Print PDF")
                }
            }
        }

        ElevatedButton(
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Delete")
        }
    }
}
