package com.bangkit.scalesappmobile.presentatiom.details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.ui.theme.SurprisedColor
import com.bangkit.scalesappmobile.ui.theme.fontFamily

@Composable
fun ScalesProperty(
    modifier: Modifier = Modifier, icon: Int, value: String,
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .width(70.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = SurprisedColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = fontFamily,
            )
        }
    }
}

@Composable
fun ScalesProperties(
    scales: ScalesDetails,
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScalesProperty(icon = R.drawable.scales, value = scales.brand)
        ScalesProperty(icon = R.drawable.scales, value = scales.kindType)
        ScalesProperty(
            icon = R.drawable.scales, value = "${scales.rangeCapacity} ${scales.unit}"
        )
        ScalesProperty(icon = R.drawable.ic_calendar, value = scales.status)
        ScalesProperty(icon = R.drawable.ic_calendar, value = scales.ratingsAverage.toString())
    }
}