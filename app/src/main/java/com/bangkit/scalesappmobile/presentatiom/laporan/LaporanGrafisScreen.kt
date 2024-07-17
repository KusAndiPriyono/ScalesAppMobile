package com.bangkit.scalesappmobile.presentatiom.laporan

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.Pie

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun LaporanGrafisScreen(

) {


    Scaffold(
        modifier = Modifier
            .padding(horizontal = 22.dp, vertical = 12.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Chart", fontSize = 18.sp)
                }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
        ) {
            item {
                Card(
                    modifier = Modifier
                        .height(300.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.elevatedCardElevation(5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                ) {
                    LineChart(
                        modifier = Modifier.padding(12.dp),
                        data = listOf(
                            Line(
                                label = "Windows",
                                values = listOf(28.0, 41.0, 5.0, 10.0, 35.0),
                                color = SolidColor(Color(0xFF23af92)),
                                firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                                secondGradientFillColor = Color.Transparent,
                                strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                                gradientAnimationDelay = 1000,
                                drawStyle = DrawStyle.Stroke(width = 2.dp),
                            )
                        ),
                        labelHelperProperties = LabelHelperProperties(
                            textStyle = TextStyle(fontSize = 12.sp, color = Color.Gray),
                        ),
                        indicatorProperties = HorizontalIndicatorProperties(
                            textStyle = TextStyle(fontSize = 12.sp, color = Color.Gray),
                        ),
                        labelProperties = LabelProperties(
                            enabled = true,
                            textStyle = TextStyle(fontSize = 12.sp, color = Color.Gray),
                            labels = listOf("Jan", "Feb", "Mar", "Apr", "May"),
                        ),
                        animationMode = AnimationMode.Together(delayBuilder = {
                            it * 500L
                        }),
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Text(
                        text = "This is a chart",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(12.dp)
                    )

                    LazyRow {
                        items(10) {
                            Card(
                                modifier = Modifier.padding(end = 12.dp),
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.elevatedCardElevation(5.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                ),
                            ) {
                                Text(
                                    text = "Item $it",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.elevatedCardElevation(5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                ) {
                    PieChart(
                        modifier = Modifier
                            .size(200.dp)
                            .padding(12.dp),
                        data = listOf(
                            Pie(
                                label = "Android",
                                data = 20.0,
                                color = Color.Red,
                                selectedColor = Color.Green
                            ),
                            Pie(
                                label = "Windows",
                                data = 45.0,
                                color = Color.Cyan,
                                selectedColor = Color.Blue
                            ),
                            Pie(
                                label = "Linux",
                                data = 35.0,
                                color = Color.Gray,
                                selectedColor = Color.Yellow
                            ),
                        ),
                        onPieClick = {
                            println("${it.label} Clicked")
//                            val pieIndex = data.indexOf(it)
//                            data =
//                                data.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
                        },
                        selectedScale = 1.2f,
                        scaleAnimEnterSpec = spring<Float>(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        colorAnimEnterSpec = tween(300),
                        colorAnimExitSpec = tween(300),
                        scaleAnimExitSpec = tween(300),
                        spaceDegreeAnimExitSpec = tween(300),
                        style = Pie.Style.Fill
                    )
                }
            }
        }
    }
}