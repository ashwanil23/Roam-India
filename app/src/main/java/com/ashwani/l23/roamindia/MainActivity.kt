    package com.ashwani.l23.roamindia

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*
import com.ashwani.l23.roamindia.ui.onboarding.OnboardingScreen
import com.ashwani.l23.roamindia.ui.theme.RoamIndiaTheme
import com.ashwani.l23.roamindia.utils.PreferencesManager
import com.ashwani.l23.roamindia.utils.Routes
import kotlinx.coroutines.delay
import androidx.compose.ui.text.input.ImeAction
import com.ashwani.l23.roamindia.ui.theme.parrot


    @Composable
fun SplashScreen(navController: NavController, context: Context) {
    val preferencesManager = remember { PreferencesManager(context) }

    LaunchedEffect(Unit) {
        delay(2500)

        // Check if onboarding has been seen
        val nextScreen = if (preferencesManager.isOnboardingSeen()) {
            Routes.MAIN_SCREEN
        } else {
            Routes.ONBOARDING_SCREEN
        }

        navController.navigate(nextScreen) {
            popUpTo("splash") { inclusive = true }
        }
    }

    // Lottie Animation
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation)).value,
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

        setContent {
            val navController = rememberNavController()

            RoamIndiaTheme {
                NavHost(
                    navController,
                    startDestination = startDestination
                ) {
                    composable(Routes.ONBOARDING_SCREEN) {
                        OnboardingScreen(navController, preferencesManager)
                    }
                    composable(Routes.SPLASH_SCREEN) {
                        SplashScreen(navController, this@MainActivity)
                    }
                    composable(Routes.MAIN_SCREEN) {
                        MainScreen()
                    }
                }
            }
        }
    }
}


@Composable
fun MainScreen() {
    Scaffold(
        topBar = { RoamIndiaTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF303030))
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Main screen content
        }
    }
}

@Composable
fun RoamIndiaTopBar(modifier: Modifier = Modifier) {
    var input by remember { mutableStateOf("") }
    val brush = remember {
        Brush.linearGradient(
            colors = listOf(
                Color.Red,
                Color(0xFFFF5722),
                Color.Yellow,
                Color.Green,
                Color.Blue,
                Color(0xFF4B0082),
                Color(0xFFEE82EE)
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF303030)),
    ) {
        Row(
            modifier = Modifier
                .padding(top = 28.dp, start = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .border(width = 0.50688.dp, color = Color(0xFF24C690), shape = RoundedCornerShape(size = 18.2475.dp))
                    .width(54.7425.dp)
                    .height(54.7425.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(18.dp))
            Column {
                Text(
                    text = "Hello Ashwani",
                    style = TextStyle(
                        fontSize = 15.21.sp,
                        fontWeight = FontWeight(600),
                        color = Color.White,
                    )
                )
                Text(
                    text = "Good Evening!",
                    style = TextStyle(
                        fontSize = 15.21.sp,
                        fontWeight = FontWeight(600),
                        color = Color.White,
                    )
                )
            }
        }
        Text(
            text = "Explore the\nIncredible India!",
            modifier = Modifier
                .padding(top = 28.dp,start = 12.dp),
            style = TextStyle(
                fontSize = 20.28.sp,
                lineHeight = 25.34.sp,
                fontWeight = FontWeight(600),
                color = Color.White,
                letterSpacing = 0.81.sp,
            )
        )

        Row {
            Box(
                modifier = Modifier
                    .weight(0.9f)
                    .padding(top = 28.dp, start = 12.dp, end = 12.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(21.dp)) // White background with rounded corners
            ) {
                Row{
                    TextField(
                        value = input,
                        onValueChange = { input = it },
                        label = {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    modifier = Modifier
                                        .border(width = 1.49395.dp, color = Color.Transparent)
                                        .padding(1.49395.dp)
                                        .width(14.1925.dp)
                                        .height(14.1925.dp),
                                    tint = Color.Black
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    modifier = Modifier
                                        .width(79.dp)
                                        .height(17.dp),
                                    text = "Search places",
                                    color = Color.Black,
                                    style = TextStyle(
                                        fontSize = 11.15.sp,
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF89807A),
                                    )
                                )
                            }
                        },
                        singleLine = true,
                        textStyle = TextStyle(brush = brush),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                // Handle search action
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent, // Remove bottom line (focused)
                            unfocusedIndicatorColor = Color.Transparent, // Remove bottom line (unfocused)
                            disabledIndicatorColor = Color.Transparent // Remove bottom line (disabled)
                        ),
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(top = 28.dp)
                    .width(54.7425.dp)
                    .height(54.7425.dp)
                    .background(color = parrot, shape = RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.weight(0.05f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoamIndiaTheme {
        MainScreen() // Only previewing the MainScreen
    }
}