import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.roamindia.travel.app.R
import com.roamindia.travel.app.screens.CustomSearchBar
import com.roamindia.travel.app.screens.EmptyStateView
import com.roamindia.travel.app.screens.LoadingView
import com.roamindia.travel.app.ui.theme.RoamIndiaTheme
import com.roamindia.travel.app.viewModel.PlaceSearchViewModel
import com.roamindia.travel.app.viewModel.WeatherViewModel
import com.roamindia.travel.app.weatherApi.NetworkResponse
import com.roamindia.travel.app.weatherApi.model.WeatherModel
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    placeSearchViewModel: PlaceSearchViewModel
) {
    var place by remember {
        mutableStateOf("")
    }
    val weatherResult = weatherViewModel.weatherResult.observeAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            CustomSearchBar(
                weatherViewModel = weatherViewModel,
                placeSearchViewModel = placeSearchViewModel
            )
        }
        item {
            when(val result = weatherResult.value) {
                is NetworkResponse.Error -> {
                    Text(text = result.message)
                }
                NetworkResponse.Loading -> {
                    LoadingView()
                }
                is NetworkResponse.Success -> {
                    WeatherDetails(data = result.data)
                }
                null -> {
                    EmptyStateView()
                }
            }
        }
    }
}

@Composable
fun WeatherDetails(
    modifier: Modifier = Modifier,
    data: WeatherModel
) {
    // Dynamic background based on weather condition
    Box(
        modifier = modifier
            .padding(
                top = dimensionResource(R.dimen.small),
                bottom = dimensionResource(R.dimen.mediumPlus)
            )
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = when {
                        data.current.is_day == "1" && data.current.condition.code == "1000" ->
                            listOf(Color(0xFF64B5F6), Color(0xFF1976D2))  // Sunny day
                        data.current.is_day == "1" ->
                            listOf(Color(0xFF90CAF9), Color(0xFF42A5F5))  // Cloudy day
                        else ->
                            listOf(Color(0xFF303F9F), Color(0xFF1A237E))  // Night
                    }
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.medium),
                    vertical = dimensionResource(R.dimen.xsmall)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with location and time info
            LocationHeader(data)

            Spacer(modifier = Modifier.height(24.dp))

            // Main temperature display
            MainTemperatureCard(data)

            Spacer(modifier = Modifier.height(16.dp))

            // Weather condition and details
            WeatherConditionCard(data)

            Spacer(modifier = Modifier.height(16.dp))

            // Wind information
            WindInfoCard(data)

            Spacer(modifier = Modifier.height(16.dp))

            // Atmospheric conditions
            AtmosphericCard(data)

            Spacer(modifier = Modifier.height(16.dp))

            // Comfort metrics (feels like, heat index, etc.)
            ComfortMetricsCard(data)

            Spacer(modifier = Modifier.height(16.dp))

            // Additional metrics
            AdditionalMetricsCard(data)

            Spacer(modifier = Modifier.height(24.dp))

            // Last updated information
            Text(
                text = "Last updated: ${data.current.last_updated}",
                style = MaterialTheme.typography.caption,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun LocationHeader(data: WeatherModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = data.location.name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = "${data.location.region}, ${data.location.country}",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.9f)
        )

        Text(
            text = "Local time: ${data.location.localtime}",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.padding(top = 4.dp)
        )

        Text(
            text = "Lat: ${data.location.lat}, Lon: ${data.location.lon}",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun MainTemperatureCard(data: WeatherModel) {
    EnhancedWeatherCard(
        backgroundColor = Color(0xFF1E88E5).copy(alpha = 0.85f),
        highlights = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
                    contentDescription = data.current.condition.text,
                    modifier = Modifier.size(110.dp)
                )

                Text(
                    text = data.current.condition.text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = data.current.temp_c,
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "°C",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                Text(
                    text = "${data.current.temp_f}°F",
                    fontSize = 18.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun WeatherConditionCard(data: WeatherModel) {
    EnhancedWeatherCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Weather Condition",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(alpha = 0.9f)
            )

            Divider(
                color = Color.White.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DetailItem(
                    label = "UV Index",
                    value = data.current.uv
                )

                DetailItem(
                    label = "Cloud Cover",
                    value = "${data.current.cloud}%"
                )

                DetailItem(
                    label = "Visibility",
                    value = "${data.current.vis_km} km"
                )
            }
        }
    }
}

@Composable
fun WindInfoCard(data: WeatherModel) {
    EnhancedWeatherCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Wind Information",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Divider(
                color = Color.White.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Wind direction compass
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.White.copy(alpha = 0.1f), CircleShape)
                            .padding(8.dp)
                    ) {
                        Canvas(modifier = Modifier.size(60.dp)) {
                            // Draw compass
                            drawCircle(
                                color = Color.White.copy(alpha = 0.3f),
                                radius = size.minDimension / 2
                            )

                            try {
                                // Calculate rotation based on wind degree
                                val windDegree = data.current.wind_degree.toIntOrNull() ?: 0
                                val angle = Math.toRadians((windDegree - 90).toDouble())
                                val arrowLength = size.minDimension / 2 - 5
                                val centerX = size.width / 2
                                val centerY = size.height / 2
                                val endX = centerX + (arrowLength * cos(angle)).toFloat()
                                val endY = centerY + (arrowLength * sin(angle)).toFloat()

                                // Draw arrow
                                drawLine(
                                    color = Color.White,
                                    start = Offset(centerX, centerY),
                                    end = Offset(endX, endY),
                                    strokeWidth = 3f
                                )
                            } catch (e: Exception) {
                                // Fallback if there's an error parsing the degree
                            }
                        }

                        Text(
                            text = data.current.wind_dir,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${data.current.wind_degree}°",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    DetailItem(
                        label = "Wind Speed",
                        value = "${data.current.wind_kph} km/h",
                        subValue = "${data.current.wind_mph} mph"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DetailItem(
                        label = "Wind Gust",
                        value = "${data.current.gust_kph} km/h",
                        subValue = "${data.current.gust_mph} mph"
                    )
                }
            }
        }
    }
}

@Composable
fun AtmosphericCard(data: WeatherModel) {
    EnhancedWeatherCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Atmospheric Conditions",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Divider(
                color = Color.White.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Pressure
                CircularGauge(
                    value = data.current.pressure_mb,
                    maxValue = "1050",
                    minValue = "950",
                    label = "Pressure",
                    unit = "mb",
                    subValue = "${data.current.pressure_in} inHg"
                )

                // Humidity
                CircularGauge(
                    value = data.current.humidity,
                    maxValue = "100",
                    minValue = "0",
                    label = "Humidity",
                    unit = "%",
                    subValue = "Dew: ${data.current.dewpoint_c}°C"
                )

                // Precipitation
                CircularGauge(
                    value = data.current.precip_mm,
                    maxValue = "25",
                    minValue = "0",
                    label = "Precip",
                    unit = "mm",
                    subValue = "${data.current.precip_in} in"
                )
            }
        }
    }
}

@Composable
fun ComfortMetricsCard(data: WeatherModel) {
    EnhancedWeatherCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Comfort Metrics",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Divider(
                color = Color.White.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Feels like temperature
                DetailItem(
                    label = "Feels Like",
                    value = "${data.current.feelslike_c}°C",
                    subValue = "${data.current.feelslike_f}°F"
                )

                // Heat index
                DetailItem(
                    label = "Heat Index",
                    value = "${data.current.heatindex_c}°C",
                    subValue = "${data.current.heatindex_f}°F"
                )

                // Wind chill
                DetailItem(
                    label = "Wind Chill",
                    value = "${data.current.windchill_c}°C",
                    subValue = "${data.current.windchill_f}°F"
                )
            }
        }
    }
}

@Composable
fun AdditionalMetricsCard(data: WeatherModel) {
    EnhancedWeatherCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Additional Information",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Divider(
                color = Color.White.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Time zone information
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Time Zone:",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )

                Text(
                    text = data.location.tz_id,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            // Day/Night indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Current Period:",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )

                Text(
                    text = if (data.current.is_day == "1") "Day time" else "Night time",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            // Condition code
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Condition Code:",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )

                Text(
                    text = data.current.condition.code,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun CircularGauge(
    value: String,
    maxValue: String,
    minValue: String,
    label: String,
    unit: String,
    subValue: String? = null
) {
    // Safe parsing with fallbacks
    val valueFloat = value.toFloatOrNull() ?: 0f
    val maxValueFloat = maxValue.toFloatOrNull() ?: 100f
    val minValueFloat = minValue.toFloatOrNull() ?: 0f

    val sweepAngle = 240f * ((valueFloat - minValueFloat) / (maxValueFloat - minValueFloat)).coerceIn(0f, 1f)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(80.dp)
    ) {
        Canvas(modifier = Modifier.size(80.dp)) {
            // Background arc
            drawArc(
                color = Color.White.copy(alpha = 0.2f),
                startAngle = 150f,
                sweepAngle = 240f,
                useCenter = false,
                style = Stroke(width = 8f, cap = StrokeCap.Round)
            )

            // Value arc
            drawArc(
                color = Color.White,
                startAngle = 150f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 8f, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = unit,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f)
            )

            if (subValue != null) {
                Text(
                    text = subValue,
                    fontSize = 10.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun DetailItem(
    label: String,
    value: String,
    subValue: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.7f)
        )

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        if (subValue != null) {
            Text(
                text = subValue,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun EnhancedWeatherCard(
    backgroundColor: Color = Color(0xFF0D47A1).copy(alpha = 0.65f),
    highlights: Boolean = false,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = backgroundColor,
        elevation = if (highlights) 8.dp else 4.dp
    ) {
        content()
    }
}
@Preview(showBackground = true)
@Composable
private fun WeatherScreenPreview() {
    RoamIndiaTheme {
        WeatherScreen(
            modifier = Modifier,
            weatherViewModel = WeatherViewModel(),
            placeSearchViewModel = PlaceSearchViewModel()
        )
    }
}