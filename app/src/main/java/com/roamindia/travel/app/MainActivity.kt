package com.roamindia.travel.app

import StateScreen
import WeatherScreen
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.roamindia.travel.app.onboarding.OnboardingScreen
import com.roamindia.travel.app.screens.MainScreen
import com.roamindia.travel.app.screens.PlaceScreen
import com.roamindia.travel.app.ui.theme.RoamIndiaTheme
import com.roamindia.travel.app.utils.PreferencesManager
import com.roamindia.travel.app.utils.Routes
import com.roamindia.travel.app.viewModel.OnboardingViewModel
import com.roamindia.travel.app.viewModel.PlaceSearchViewModel
import com.roamindia.travel.app.viewModel.WeatherViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, context: Context) {
    val preferencesManager = remember { PreferencesManager(context) }

    LaunchedEffect(Unit) {
        delay(2500)
        navController.navigate(
            if (preferencesManager.isOnboardingSeen()) Routes.MAIN_SCREEN
            else Routes.ONBOARDING_SCREEN
        ) {
            popUpTo(Routes.SPLASH_SCREEN) { inclusive = true }
        }
    }

    AnimatedSplashContent()
}

@Composable
private fun AnimatedSplashContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.splash_animation)
            ).value,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(300.dp)
        )
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        val preferencesManager = PreferencesManager(this)
        val startDestination = if (preferencesManager.isOnboardingSeen()) {
            Routes.SPLASH_SCREEN
        } else {
            Routes.ONBOARDING_SCREEN
        }
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        val placeSearchViewModel = ViewModelProvider(this)[PlaceSearchViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            RoamIndiaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RoamIndiaApp(
                        modifier = Modifier.padding(innerPadding),
                        startDestination = startDestination,
                        preferencesManager = preferencesManager,
                        weatherViewModel = weatherViewModel,
                        placeSearchViewModel = placeSearchViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun RoamIndiaApp(
    modifier: Modifier = Modifier,
    startDestination: String,
    preferencesManager: PreferencesManager,
    onboardingViewModel: OnboardingViewModel = viewModel(),
    weatherViewModel: WeatherViewModel,
    placeSearchViewModel: PlaceSearchViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController,startDestination = startDestination) {
        composable(route = Routes.ONBOARDING_SCREEN) {
            OnboardingScreen(navController, preferencesManager, onboardingViewModel)
        }
        composable(route = Routes.SPLASH_SCREEN) {
            SplashScreen(navController, LocalContext.current)
        }
        composable(route = Routes.MAIN_SCREEN) {
            MainScreen(
                modifier = modifier,
                onCheckedButtonClicked = { navController.navigate(Routes.STATE_SCREEN) },
                onWeatherButtonClicked = { navController.navigate(Routes.WEATHER_SCREEN) },
                placeSearchViewModel = placeSearchViewModel,
                weatherViewModel = weatherViewModel
            )
        }
        composable(route = Routes.STATE_SCREEN){
            StateScreen(
                onCheckedButtonClicked = {navController.navigate(Routes.PLACE_SCREEN)}
            )
        }
        composable(route = Routes.PLACE_SCREEN){
            PlaceScreen(
                onCheckedButtonClicked = {}
            )
        }
        composable(route = Routes.WEATHER_SCREEN) {
            WeatherScreen(
                weatherViewModel = weatherViewModel,
                placeSearchViewModel = placeSearchViewModel
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoamIndiaTheme {
    }
}