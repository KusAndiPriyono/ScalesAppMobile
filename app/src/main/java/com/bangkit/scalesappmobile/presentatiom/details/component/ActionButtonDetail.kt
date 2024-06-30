package com.bangkit.scalesappmobile.presentatiom.details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.ui.theme.SurprisedColor
import com.bangkit.scalesappmobile.ui.theme.fontFamily

@Composable
fun ActionButtonDetail(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    color: Color = SurprisedColor,
    iconTint: Color = MaterialTheme.colorScheme.onBackground,
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .width(70.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = onClick
            ) {
                Icon(imageVector = icon, contentDescription = text, tint = iconTint)
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = fontFamily
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonDetailPreview() {
    ActionButtonDetail(
        text = "Calibrate",
        icon = ImageVector.vectorResource(id = R.drawable.ic_notification),
        onClick = { /*TODO*/ }
    )
}