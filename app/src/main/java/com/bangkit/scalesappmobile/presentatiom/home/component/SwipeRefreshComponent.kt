package com.bangkit.scalesappmobile.presentatiom.home.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun Modifier.shimmerEffect(cornerRadius: CornerRadius = CornerRadius(x = 12f, y = 12f)) = composed {
    val transition = rememberInfiniteTransition(label = "shimmer effect")
    val alpha = transition.animateFloat(
        initialValue = 0.2f, targetValue = 0.9f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "transparency of the background color"
    ).value
    val color = MaterialTheme.colorScheme.outline.copy(alpha = alpha)
    drawBehind {
        drawRoundRect(
            color = color,
            cornerRadius = cornerRadius
        )
    }
}

@Composable
fun ScalesCardShimmerEffect(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
                .padding(vertical = 8.dp)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScalesCardShimmerEffectPreview() {
    MaterialTheme {
        ScalesCardShimmerEffect()
    }
}