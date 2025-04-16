package com.roamindia.travel.app.screens

import ErrorView
import LocationHeader
import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.roamindia.travel.app.MainActivity
import com.roamindia.travel.app.R
import com.roamindia.travel.app.ui.theme.RoamIndiaTheme
import com.roamindia.travel.app.utils.MyLocationUtils
import com.roamindia.travel.app.utils.Routes
import com.roamindia.travel.app.viewModel.CurrentLocationViewModel
import com.roamindia.travel.app.viewModel.PlaceSearchViewModel
import com.roamindia.travel.app.viewModel.WeatherViewModel
import com.roamindia.travel.app.weatherApi.NetworkResponse
import com.roamindia.travel.app.weatherApi.model.WeatherModel
import java.time.LocalTime

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onWeatherButtonClicked: ()->Unit,
    onCheckedButtonClicked: () -> Unit,
    onAddDropDownClicked: () -> Unit,
    placeSearchViewModel: PlaceSearchViewModel,
    weatherViewModel: WeatherViewModel,
    currentLocationViewModel: CurrentLocationViewModel,
    currentLocationUtils: MyLocationUtils,
    context: Context
) {
    val currentHour = LocalTime.now().hour
    val backgroundColor = when (currentHour) {
        in 5..11 -> Color(0xFFFFF59D)
        in 12..16 -> Color(0xFF90CAF9)
        in 17..19 -> Color(0xFFFFAB91)
        else -> Color(0xFFB39DDB)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(backgroundColor)
            .padding(
                horizontal = dimensionResource(R.dimen.medium),
                vertical = dimensionResource(R.dimen.medium)
            )
    ) {
        Row {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp),
                shape = RoundedCornerShape(dimensionResource(R.dimen.xsmall)),
                colors = CardDefaults.elevatedCardColors(Color.Transparent)
            ) {
                ProfileSection(
                    onAddDropDownClicked = onAddDropDownClicked,
                    currentLocationViewModel = currentLocationViewModel,
                    currentLocationUtils = currentLocationUtils,
                    context = context

                )
            }


            Spacer(modifier.padding(dimensionResource(R.dimen.small)))

            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp),
                elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.small)),
                shape = RoundedCornerShape(dimensionResource(R.dimen.xsmall)),
                colors = CardDefaults.elevatedCardColors(Color.LightGray),
                onClick = onWeatherButtonClicked,
                border = BorderStroke(width = 1.dp, color = Color.Black)
            ) {
                WeatherSection(weatherViewModel = weatherViewModel)
            }
        }
        Text(
            text = "State",
            modifier = Modifier
                .padding(vertical = dimensionResource(R.dimen.medium)),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )
        val weatherResult by weatherViewModel.weatherResult.observeAsState()

        val cardColor = when (weatherResult) {
            is NetworkResponse.Error -> CardDefaults.elevatedCardColors(Color.LightGray)
            is NetworkResponse.Loading -> CardDefaults.elevatedCardColors(Color.LightGray)
            is NetworkResponse.Success -> {
                val currentHour = LocalTime.now().hour
                val backgroundColor = when (currentHour) {
                    in 5..11 -> Color(0xFFFFF59D)
                    in 12..16 -> Color(0xFF90CAF9)
                    in 17..19 -> Color(0xFFFFAB91)
                    else -> Color(0xFFB39DDB)
                }
                CardDefaults.elevatedCardColors(backgroundColor)
            }
            null -> CardDefaults.elevatedCardColors(Color.LightGray)
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.xsmall)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.small)),
            colors = cardColor,
            border = BorderStroke(width = 1.dp, color = Color.Black)
        ) {
            Column {
                CustomSearchBar(
                    placeSearchViewModel= placeSearchViewModel,
                    weatherViewModel = weatherViewModel
                )
                StateListView(
                    modifier = Modifier
                        .height(450.dp),
                    onCheckedButtonClicked = onCheckedButtonClicked
                )
            }
        }
        Spacer(Modifier.padding(dimensionResource(R.dimen.medium)))
        EndOfAppView()

    }
}

@Composable
fun ChatBot() {

}

@Composable
fun EndOfAppView() {
    Column {
        Text(
            text = "Made with ❤️",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        Text(
            text = "In India",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun CustomSearchBar(
    placeSearchViewModel: PlaceSearchViewModel,
    weatherViewModel: WeatherViewModel
) {
    var place by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.medium)
            ),
        value = place,
        onValueChange = { place = it },
        label = {
            Text(
                text = "Enter Place"
            )
        },
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = {
                    weatherViewModel.getWeatherData(place)
                    placeSearchViewModel.getPlaceData(place)
                    keyboardController?.hide()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                )
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            weatherViewModel.getWeatherData(place);
            placeSearchViewModel.getPlaceData(place)
            keyboardController?.hide()
        })
    )
}

@Composable
fun StateListView(
    modifier: Modifier,
    onCheckedButtonClicked: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ){
        items(10) {
            Box(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.medium),
                        end = dimensionResource(R.dimen.medium),
                        top = dimensionResource(R.dimen.medium)
                    )
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFFFDFD),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.medium))
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.xmedium)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.andhra),
                        contentDescription = "image of andhra pradesh",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(dimensionResource(R.dimen.xmedium)))
                            .width(75.0175.dp)
                            .height(55.75625.dp)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(dimensionResource(R.dimen.small))
                    ) {
                        Text(
                            text = "Andhra Pradesh",
                            modifier = Modifier
                                .width(109.dp)
                                .height(20.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(9.70415.dp)
                                    .height(12.35072.dp),
                                tint = colorResource(R.color.parrot_button)
                            )
                            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.xsmall)))
                            Text(
                                text = "India",
                                style = TextStyle(
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF89807A)
                                ),
                                modifier = Modifier
                                    .width(109.dp)
                                    .height(20.dp)
                            )
                        }
                    }
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        onClick = onCheckedButtonClicked,
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.parrot_button))
                    ) {
                        Text(text = "Visit")
                    }
                }
            }
        }
        item {
            Spacer(Modifier.padding(bottom = dimensionResource(R.dimen.medium)))
        }
    }
}

@Composable
fun WeatherSection(weatherViewModel: WeatherViewModel) {
    val weatherResult = weatherViewModel.weatherResult.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when(val result = weatherResult.value) {
            is NetworkResponse.Error -> {
                ErrorView(result.message)
            }
            NetworkResponse.Loading -> {
                LoadingView()
            }
            is NetworkResponse.Success -> {
                WeatherSectionDetails(data = result.data)
            }
            null -> {
                EmptyStateView(modifier = Modifier)
            }
        }
    }
}
@Composable
fun LoadingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Loading weather...",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun EmptyStateView(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.search_png),
            contentDescription = "Search Png",
            contentScale = ContentScale.Inside
        )
    }
}
@Composable
fun WeatherSectionDetails(
    modifier: Modifier = Modifier,
    data: WeatherModel
) {
    // Dynamic background based on weather condition
    Box(
        modifier = modifier
            .fillMaxSize()
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with location and time info
            LocationHeader(data)
        }
    }
}
@Composable
fun ProfileSection(
    onAddDropDownClicked: () -> Unit,
    currentLocationUtils: MyLocationUtils,
    currentLocationViewModel: CurrentLocationViewModel,
    context: Context,
) {
    val location = currentLocationViewModel.location.value
    var addressText by remember { mutableStateOf("Address not available") }

    LaunchedEffect(location) {
        location?.let {
            try {
                addressText = currentLocationUtils.requestGeocodeLocation(it)
            } catch (e: Exception) {
                addressText = "Could not retrieve address"
            }
        }
    }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .border(
                            width = 0.50688.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(dimensionResource(R.dimen.small))
                        )
                        .size(dimensionResource(R.dimen.xlarge)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.padding(dimensionResource(R.dimen.xxsmall)))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Hi!",
                    Modifier
                        .fillMaxWidth(),
                    fontSize = 32.sp,
                )
                Text(
                    text = "Traveller",
                    Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = addressText,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                fontSize = 12.sp,
                maxLines = 2
            )
            IconButton(
                onClick = onAddDropDownClicked
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(dimensionResource(R.dimen.medium)),
                )
            }

        }
    }
}

@Preview(name = "Main Screen", showBackground = true)
@Composable
private fun MainScreenPreview() {
    val navController = rememberNavController()
    RoamIndiaTheme {
        MainScreen(
            onWeatherButtonClicked = { navController.navigate(Routes.WEATHER_SCREEN) },
            onCheckedButtonClicked = { navController.navigate(Routes.STATE_SCREEN) },
            onAddDropDownClicked = { navController.navigate(Routes.CURRENT_LOCATION_SCREEN) },
            weatherViewModel = WeatherViewModel(),
            placeSearchViewModel = PlaceSearchViewModel(),
            currentLocationViewModel = CurrentLocationViewModel(),
            context = LocalContext.current,
            currentLocationUtils = MyLocationUtils(context = LocalContext.current),
        )
    }
}
