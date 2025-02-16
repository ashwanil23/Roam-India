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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*
import com.ashwani.l23.roamindia.pages.PlaceScreen
import com.ashwani.l23.roamindia.pages.StateScreen
import com.ashwani.l23.roamindia.ui.onboarding.OnboardingScreen
import com.ashwani.l23.roamindia.ui.theme.RoamIndiaTheme
import com.ashwani.l23.roamindia.ui.theme.parrot
import com.ashwani.l23.roamindia.utils.PreferencesManager
import com.ashwani.l23.roamindia.utils.Routes
import kotlinx.coroutines.delay


    // Constants and styles
    internal object AppDimensions {
        val stateTileWidth = 333.52374.dp
        val stateTileHeight = 82.11375.dp
        val iconSize = 54.7425.dp
        val cornerRadius = 18.2475.dp
        val searchBarPadding = 28.dp
    }

    internal object AppStyles {
        val titleTextStyle = TextStyle(
            fontSize = 20.28.sp,
            lineHeight = 25.34.sp,
            fontWeight = FontWeight(600),
            color = Color.White,
            letterSpacing = 0.81.sp
        )

        val headerTextStyle = TextStyle(
            fontSize = 15.21.sp,
            fontWeight = FontWeight(600),
            color = Color.White
        )

        val stateNameStyle = TextStyle(
            fontSize = 13.18.sp,
            fontWeight = FontWeight(600),
            color = Color(0xFF000000)
        )
    }

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

            setContent {
                RoamIndiaApp(startDestination, preferencesManager)
            }
        }
    }

    @Composable
    private fun RoamIndiaApp(startDestination: String, preferencesManager: PreferencesManager) {
        val navController = rememberNavController()

        RoamIndiaTheme {
            NavHost(navController, startDestination = startDestination) {
                composable(route = Routes.ONBOARDING_SCREEN) {
                    OnboardingScreen(navController, preferencesManager)
                }
                composable(route = Routes.SPLASH_SCREEN) {
                    SplashScreen(navController, LocalContext.current)
                }
                composable(route = Routes.MAIN_SCREEN) {
                    MainScreen(onCheckButtonClicked = {navController.navigate(Routes.STATE_SCREEN)} )
                }
                composable(route = Routes.STATE_SCREEN){
                    StateScreen(onCheckButtonClicked = {navController.navigate(Routes.PLACE_SCREEN)})
                }
                composable(route = Routes.PLACE_SCREEN){
                    PlaceScreen(onCheckButtonClicked = {})
                }
            }
        }
    }

    @Composable
    fun MainScreen(
        onCheckButtonClicked: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF303030))
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                RoamIndiaTopBar()
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    MainContent(
                        PaddingValues(),
                        onCheckButtonClicked = onCheckButtonClicked
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 4.dp)
            ) {
                RoamIndiaBottomBar()
            }
        }
    }

    @Composable
    fun RoamIndiaBottomBar() {
        Box(
            modifier = Modifier
                .width(340.dp)
                .height(72.dp)
                .clip(RoundedCornerShape(37.dp))
                .background(Color(0xFF222222)) // Proper background
        ) {
            BottomNavigation(
                backgroundColor = Color.Transparent, // Fix unwanted background
                elevation = 0.dp,
                contentColor = Color.White,
                modifier = Modifier.fillMaxSize()
            ) {
                repeat(4) { index ->
                    BottomNavigationItem(
                        selected = index == 0,
                        onClick = {}, // Add navigation action
                        icon = {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .size(48.dp) // Controls the touch area
                                    .background(Color.Transparent) // Ensures no extra background
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Place,
                                    contentDescription = "Navigation Icon",
                                    modifier = Modifier.size(24.dp) // Adjust icon size
                                )
                            }
                        },
                        label = {
                            Text(
                                text = "Go",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (index == 0) Color(0xFFB5E848) else Color.White
                                )
                            )
                        },
                        selectedContentColor = Color(0xFFB5E848),
                        unselectedContentColor = Color(0xAAFFFFFF)
                    )
                }
            }
        }
    }




    @Composable
    private fun MainContent(innerPadding: PaddingValues, onCheckButtonClicked: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF303030))
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppSearchBar()
            StatesListElement(onCheckButtonClicked = onCheckButtonClicked)
        }
    }

    @Composable
    fun StatesListElement(onCheckButtonClicked: () -> Unit) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            StateTile(onCheckButtonClicked = onCheckButtonClicked)
        }
    }

    @Composable
    private fun StateTile(onCheckButtonClicked: () -> Unit) {
        repeat(10){
            Box(
                modifier = Modifier
                    .width(AppDimensions.stateTileWidth)
                    .height(AppDimensions.stateTileHeight)
                    .background(
                        color = Color(0xFFFFFDFD),
                        shape = RoundedCornerShape(AppDimensions.cornerRadius)
                    )
            ) {
                StateTileContent(onCheckButtonClicked = onCheckButtonClicked)
            }
        }

    }

    @Composable
    private fun StateTileContent(onCheckButtonClicked: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(13.18.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StateImage()
            StateInfo()
            CheckButton(onCheckButtonClicked = onCheckButtonClicked)
        }
    }

    @Composable
    private fun StateImage() {
        Image(
            painter = painterResource(R.drawable.andhra),
            contentDescription = "image of andhra pradesh",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(13.18.dp))
                .width(75.0175.dp)
                .height(55.75625.dp)
        )
    }

    @Composable
    private fun StateInfo() {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "Andhra Pradesh",
                style = AppStyles.stateNameStyle,
                modifier = Modifier
                    .width(109.dp)
                    .height(20.dp)
            )
            LocationInfo()
        }
    }

    @Composable
    private fun LocationInfo() {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                modifier = Modifier
                    .width(9.70415.dp)
                    .height(12.35072.dp),
                tint = parrot
            )
            Spacer(modifier = Modifier.size(4.dp))
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

    @Composable
    private fun CheckButton(onCheckButtonClicked: () -> Unit) {
        Button(
            onClick = {onCheckButtonClicked()},
            colors = ButtonDefaults.buttonColors(parrot)
        ) {
            Text(text = "Check")
        }
    }

    @Composable
    fun AppSearchBar() {
        var input by remember { mutableStateOf("") }
        val rainbowBrush = remember {
            Brush.linearGradient(
                colors = listOf(
                    Color.Red, Color(0xFFFF5722), Color.Yellow,
                    Color.Green, Color.Blue, Color(0xFF4B0082), Color(0xFFEE82EE)
                )
            )
        }

        Column {
            SearchBarContent(input, { input = it }, rainbowBrush)
            SelectStateHeader()
        }
    }

    @Composable
    private fun SearchBarContent(
        input: String,
        onInputChange: (String) -> Unit,
        textBrush: Brush
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f) // Take available space but leave room for filter
                    .padding(
                        top = AppDimensions.searchBarPadding,
                        start = 12.dp,
                        end = 12.dp
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(21.dp)
                    )
            ) {
                CustomTextField(input, onInputChange, textBrush)
            }
            FilterButton()
        }
    }

    @Composable
    private fun SearchTextField(
        input: String,
        onInputChange: (String) -> Unit,
        textBrush: Brush
    ) {
        Box(
            modifier = Modifier
                .padding(
                    top = AppDimensions.searchBarPadding,
                    start = 12.dp,
                    end = 12.dp
                )

                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(21.dp)
                )
        ) {
            CustomTextField(input, onInputChange, textBrush)
        }
    }

    @Composable
    private fun CustomTextField(
        input: String,
        onInputChange: (String) -> Unit,
        textBrush: Brush
    ) {
        TextField(
            value = input,
            onValueChange = onInputChange,
            label = { SearchLabel() },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(brush = textBrush),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default
            ),
            keyboardActions = KeyboardActions(onSearch = {}),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }

    @Composable
    private fun SearchLabel() {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchIcon()
            Spacer(modifier = Modifier.width(12.dp))
            SearchText()
        }
    }

    @Composable
    private fun SearchIcon() {
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
    }

    @Composable
    private fun SearchText() {
        Text(
            modifier = Modifier
                .width(79.dp)
                .height(17.dp),
            text = "Search places",
            style = TextStyle(
                fontSize = 11.15.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF89807A)
            )
        )
    }

    @Composable
    private fun FilterButton() {
        Box(
            modifier = Modifier
                .padding(top = AppDimensions.searchBarPadding, end = 12.dp)
                .size(AppDimensions.iconSize)
                .background(
                    color = parrot,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.align(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(R.drawable.sharp_display_settings_24),
                    contentDescription = "Filter",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.size(AppDimensions.iconSize)
                )
            }
        }
    }

    @Composable
    private fun SelectStateHeader() {
        Row(
            modifier = Modifier.padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StateSelectionTitle()
            Spacer(modifier = Modifier.weight(1f))
            ShowMoreButton()
        }
    }

    @Composable
    private fun StateSelectionTitle() {
        Text(
            text = "Select State",
            style = TextStyle(
                fontSize = 18.25.sp,
                fontWeight = FontWeight(600),
                color = parrot
            ),
            modifier = Modifier
                .padding(start = 12.dp)
                .width(111.dp)
                .height(27.dp)
        )
    }

    @Composable
    private fun ShowMoreButton() {
        TextButton(onClick = {}) {
            Text(
                text = "Show more ›",
                style = TextStyle(
                    fontSize = 11.15.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xB2FFFFFF)
                ),
                modifier = Modifier
                    .width(64.dp)
                    .height(17.dp)
            )
        }
    }

    @Composable
    fun RoamIndiaTopBar(modifier: Modifier = Modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF303030))
        ) {
            TopBarHeader()
            WelcomeMessage()
        }
    }

    @Composable
    private fun TopBarHeader() {
        Row(
            modifier = Modifier
                .padding(
                    top = AppDimensions.searchBarPadding,
                    start = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ProfileImage()
            Spacer(modifier = Modifier.width(18.dp))
            GreetingText()
        }
    }

    @Composable
    private fun ProfileImage() {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .border(
                    width = 0.50688.dp,
                    color = Color(0xFF24C690),
                    shape = RoundedCornerShape(AppDimensions.cornerRadius)
                )
                .size(AppDimensions.iconSize),
            contentScale = ContentScale.Crop
        )
    }

    @Composable
    private fun GreetingText() {
        Column {
            Text(
                text = "Hello Ashwani",
                style = AppStyles.headerTextStyle
            )
            Text(
                text = "Good Evening!",
                style = AppStyles.headerTextStyle
            )
        }
    }

    @Composable
    private fun WelcomeMessage() {
        Text(
            text = "Explore the\nIncredible India!",
            modifier = Modifier.padding(
                top = AppDimensions.searchBarPadding,
                start = 12.dp
            ),
            style = AppStyles.titleTextStyle
        )
    }

    @Preview(
        name = "Component Group",
        showBackground = true,
        widthDp = 400,
        heightDp = 800
    )
    @Composable
    fun ComponentGroupPreview(navController: NavHostController = rememberNavController()) {
        RoamIndiaTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF303030))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RoamIndiaTopBar()
                AppSearchBar()
                StateTile(onCheckButtonClicked = {navController.navigate(Routes.STATE_SCREEN)})
                FilterButton()
                LocationInfo()
                RoamIndiaBottomBar()
            }
        }
    }