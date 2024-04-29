package com.bangkit.scalesappmobile.presentatiom.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.ui.theme.AngryColor
import com.bangkit.scalesappmobile.ui.theme.NeutralColor

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = AngryColor,
    unselectedColor: Color = NeutralColor
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(times = pageSize) { page ->
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PageIndicatorPreview() {
    PageIndicator(pageSize = 3, selectedPage = 1)
}