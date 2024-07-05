package com.bangkit.scalesappmobile.presentatiom.details.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.domain.model.AllReviews
import com.bangkit.scalesappmobile.domain.model.User
import com.bangkit.scalesappmobile.presentatiom.common.FormatStringToDate
import com.bangkit.scalesappmobile.presentatiom.common.StarRatingBar
import com.bangkit.scalesappmobile.ui.theme.cardColorCustom
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import java.time.ZonedDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardReviewContent(
    allReviews: AllReviews,
    index: Int, // Add an index parameter to select color
) {
    val context = LocalContext.current
    val cardColor = cardColorCustom[index % cardColorCustom.size] // Select color based on index
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor) // Set the background color
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .size(50.dp),
                    model = ImageRequest.Builder(context)
                        .crossfade(true)
                        .data(allReviews.user.photo)
                        .error(R.drawable.ic_account_circle)
                        .build(),
                    contentDescription = null,
                )
                Column {
                    Text(
                        text = allReviews.user.name, style = MaterialTheme.typography.titleSmall,
                        fontFamily = fontFamily,
                        color = MaterialTheme.colorScheme.scrim,
                        overflow = TextOverflow.Ellipsis
                    )
                    StarRatingBar(
                        rating = allReviews.rating.toFloat(),
                        onRatingChanged = { allReviews.rating },
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FormatStringToDate(
                        dateString = allReviews.createdAt,
                        color = MaterialTheme.colorScheme.scrim
                    )
                }
            }
            Text(
                text = allReviews.review,
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleSmall,
                fontFamily = fontFamily,
                color = MaterialTheme.colorScheme.scrim,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CardReviewPreview() {
    CardReviewContent(
        allReviews = AllReviews(
            createdAt = ZonedDateTime.now().toString(),
            id = "1",
            rating = 5,
            review = "This is a review",
            user = User(
                id = "1",
                name = "John Doe",
                photo = "https://randomuser.me/api/portraits",
                email = "",
                role = "",
            ),
            scale = null.toString(),
        ),
        index = 0 // Example index
    )
}
