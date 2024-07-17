package com.bangkit.scalesappmobile.presentatiom.kalibrasi.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.presentatiom.home.component.UserRole

@Composable
fun ActionButtonApproved(
    userRole: UserRole,
    statusApproval: ApprovalStatus,
    onClickApproved: () -> Unit,
    onClickRejected: () -> Unit,
) {

    if (userRole == UserRole.fromString("manager")) {
        if (statusApproval == ApprovalStatus.fromString("waiting")) {
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
                    onClick = {
                        onClickRejected()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background
                    )
                    Text(text = "Reject")
                }

                ElevatedButton(
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    onClick = {
                        onClickApproved()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background
                    )
                    Text(text = "Approve")
                }
            }
        }
    }
}