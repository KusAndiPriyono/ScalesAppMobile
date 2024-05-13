package com.bangkit.scalesappmobile.presentatiom.home.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Suppress("DEPRECATION")
@Composable
fun SwiperRefreshData(
    isRefreshingState: Boolean,
    onRefreshData: () -> Unit,
    content: @Composable () -> Unit,
) {

    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isRefreshingState), onRefresh = {
        onRefreshData()
    }, indicator = { state, refreshTrigger ->
        SwipeRefreshIndicator(
            state = state,
            refreshTriggerDistance = refreshTrigger,
            scale = true,
            arrowEnabled = true,
            refreshingOffset = 120.dp
        )
    }) {
        content()
    }
}

//fun Modifier.shimmerEffect(cornerRadius: CornerRadius = CornerRadius(x = 12f, y = 12f)) = composed {
//    val transition = rememberInfiniteTransition(label = "shimmer effect")
//    val alpha = transition.animateFloat(
//        initialValue = 0.2f, targetValue = 0.9f, animationSpec = infiniteRepeatable(
//            animation = tween(durationMillis = 1000),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "transparency of the background color"
//    ).value
//    val color = MaterialTheme.colorScheme.outline.copy(alpha = alpha)
//    drawBehind {
//        drawRoundRect(
//            color = color,
//            cornerRadius = cornerRadius
//        )
//    }
//}
//
//@Composable
//fun ScalesCardShimmerEffect(
//    modifier: Modifier = Modifier,
//) {
//    Column(
//        modifier = modifier
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .size(250.dp)
//                .padding(vertical = 8.dp)
//                .clip(MaterialTheme.shapes.medium)
//                .shimmerEffect()
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ScalesCardShimmerEffectPreview() {
//    MaterialTheme {
//        ScalesCardShimmerEffect()
//    }
//}