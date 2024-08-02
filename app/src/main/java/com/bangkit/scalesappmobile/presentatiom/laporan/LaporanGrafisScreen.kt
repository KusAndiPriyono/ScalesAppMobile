package com.bangkit.scalesappmobile.presentatiom.laporan

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.scalesappmobile.ui.theme.fontFamily
import com.bangkit.scalesappmobile.ui.theme.pieColorCustom
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
    viewModel: LaporanGrafisViewModel = hiltViewModel()
) {
    val scalesGrafis = viewModel.scalesGrafisState.value
    val totalItems by viewModel.totalItems

    val values = scalesGrafis.data.map { it.ratingsAverage }
    val labels = scalesGrafis.data.map { it.location }

    // Mengelompokkan data berdasarkan lokasi
    val locationMap = scalesGrafis.data.groupBy { it.location }
        .mapValues { it.value.size }

    val pieData = locationMap.entries.mapIndexed { index, entry ->
        Pie(
            label = entry.key,
            data = (entry.value.toFloat() / totalItems) * 100.0,
            color = pieColorCustom[index % pieColorCustom.size],
            selectedColor = pieColorCustom[index % pieColorCustom.size],
        )
    }

    var selectedPieIndex by remember { mutableIntStateOf(-1) }

    Scaffold(
        modifier = Modifier
            .padding(horizontal = 22.dp, vertical = 12.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Thin,
                            fontFamily = fontFamily
                        ),
                        text = "Presentase Data Grafis"
                    )
                }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
        ) {
            item {
                if (scalesGrafis.data.isNotEmpty()) {
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
                                    label = "Analisis Rating Review",
                                    values = values,
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
                                labels = labels,
                            ),
                            animationMode = AnimationMode.Together(delayBuilder = {
                                it * 500L
                            }),
                        )
                    }
                } else {
                    // Tampilkan placeholder atau pesan jika data kosong
                    Text(
                        text = "No data available",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.Thin,
                        fontFamily = fontFamily
                    ),
                    text = "Presentase data berdasarkan lokasi:"
                )
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (pieData.isNotEmpty()) {
                            PieChart(
                                modifier = Modifier
                                    .size(200.dp)
                                    .padding(24.dp),
                                data = pieData.mapIndexed { index, pie ->
                                    pie.copy(selected = index == selectedPieIndex)
                                },
                                onPieClick = {
                                    // Handle pie click
                                    println("${it.label} Clicked")
                                    val pieIndex = pieData.indexOf(it)
                                    selectedPieIndex = pieIndex
                                },
                                selectedScale = 1.2f,
                                scaleAnimEnterSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                ),
                                colorAnimEnterSpec = tween(300),
                                colorAnimExitSpec = tween(300),
                                scaleAnimExitSpec = tween(300),
                                spaceDegreeAnimExitSpec = tween(300),
                                style = Pie.Style.Fill
                            )
                        } else {
                            Text(
                                text = "No data available for Pie Chart",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        // Tampilkan detail lokasi yang dipilih
                        if (selectedPieIndex != -1) {
                            val selectedPie = pieData[selectedPieIndex]
                            Text(
                                text = "Selected: ${selectedPie.label} (${selectedPie.data.toInt()}%) dari total ${locationMap.values.sum()} data",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                    fontWeight = FontWeight.Thin,
                                    fontFamily = fontFamily
                                ),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
