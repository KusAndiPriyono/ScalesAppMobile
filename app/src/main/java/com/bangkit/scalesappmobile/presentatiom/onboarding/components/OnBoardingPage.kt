package com.bangkit.scalesappmobile.presentatiom.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.scalesappmobile.ui.theme.AngryColor
import com.bangkit.scalesappmobile.ui.theme.ScalesAppMobileTheme
import com.bangkit.scalesappmobile.ui.theme.fontFamily

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
    color: Color = AngryColor
) {
    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = page.title,
            modifier = Modifier.padding(horizontal = 30.dp),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            fontFamily = fontFamily,
            color = color
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = page.description,
            modifier = Modifier.padding(horizontal = 30.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = fontFamily,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPagePreview() {
    ScalesAppMobileTheme {
        OnBoardingPage(page = onBoardingPages[0])
    }
}