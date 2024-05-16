package com.bangkit.scalesappmobile.presentatiom.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.presentatiom.onboarding.components.PageIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banner(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val bannerImages = listOf(
        R.drawable.banner_homepage_kopiko_astronot,
        R.drawable.banner_mayora_1,
        R.drawable.produkmayora,
    )

    Box(modifier = modifier) {
        val pagerState = rememberPagerState(initialPage = 0) {
            bannerImages.size
        }
        Column {

            LaunchedEffect(Unit) {
                while (true) {
                    yield()
                    delay(2600)

                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage + 1) % (pagerState.pageCount)
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = modifier
                    .height(160.dp)
                    .fillMaxWidth()
            ) { page ->
                val pageOffset =
                    (pagerState.currentPage - page + pagerState.currentPageOffsetFraction).absoluteValue
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .graphicsLayer {
                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }

                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                ) {
                    Image(
                        painter = painterResource(id = bannerImages[page]),
                        contentDescription = stringResource(R.string.banner_image_description),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        PageIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(52.dp)
                .padding(bottom = 8.dp),
            pageSize = bannerImages.size,
            selectedPage = pagerState.currentPage,
        )
        SearchBox(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = { onClick() },
        )
    }
}


@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                onClick()
            },
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.6f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
            Text(
                text = "Search for a scale...",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBoxPreview() {
    SearchBox {}
}