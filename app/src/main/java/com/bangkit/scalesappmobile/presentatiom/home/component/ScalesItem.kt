package com.bangkit.scalesappmobile.presentatiom.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.domain.model.Scales

@Composable
fun ScalesItem(
    scales: Scales,
    onClick: (String) -> Unit,
    addToFavorites: (scales: Scales) -> Unit,
    removeFromFavorites: (scalesId: String) -> Unit,
    isFavorite: (scalesId: String) -> Boolean,
) {
    val context = LocalContext.current
    val favorite = isFavorite(scales.id)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 5.dp)
            .clickable { onClick(scales.id) },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentDescription = scales.name,
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(data = scales.imageCover)
                        .apply(
                            block = fun ImageRequest.Builder.() {
                                placeholder(R.drawable.logo)
                            }
                        ).build()
                ),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(vertical = 3.dp),
                    text = scales.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    onClick = {
                        if (favorite) {
                            removeFromFavorites(scales.id)
                        } else {
                            addToFavorites(scales)
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp),
                        painter = if (favorite) {
                            painterResource(id = R.drawable.filled_favorite)
                        } else {
                            painterResource(id = R.drawable.heart_plus)
                        },
                        contentDescription = null,
                        tint = if (favorite) {
                            Color(0xFFfa4a0c)
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScalesItemPreview() {
    ScalesItem(
        scales = Scales(
            id = "1",
            name = "Scales 1",
            imageCover = "https://www.google.com",
        ),
        onClick = {},
        addToFavorites = {},
        removeFromFavorites = {},
        isFavorite = { false }
    )
}