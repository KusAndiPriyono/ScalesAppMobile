package com.bangkit.scalesappmobile.presentatiom.kalibrasi.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.domain.model.AllForm

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