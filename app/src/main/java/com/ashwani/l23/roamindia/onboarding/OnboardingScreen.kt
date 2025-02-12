package com.ashwani.l23.roamindia.ui.onboarding

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ashwani.l23.roamindia.model.OnboardingDataRes.onboardingDataList
import com.ashwani.l23.roamindia.ui.theme.parrot
import com.ashwani.l23.roamindia.utils.PreferencesManager
import com.ashwani.l23.roamindia.utils.Routes
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(navController: NavController, preferencesManager: PreferencesManager) {
    var pageIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .background(color = onboardingDataList[pageIndex].color),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Image(
            painter = painterResource(onboardingDataList[pageIndex].imageRes),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Text(text = onboardingDataList[pageIndex].description, fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center, // Centering the row properly
            verticalAlignment = Alignment.CenterVertically
        ) {
            onboardingDataList.indices.forEach { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            if (pageIndex == index) parrot else Color.Gray,
                            CircleShape
                        )
                )
                if (index != onboardingDataList.lastIndex) {
                    Spacer(Modifier.width(8.dp)) // Use width instead of padding for consistency
                }
            }
        }

        Button(
            colors = ButtonDefaults.buttonColors(parrot),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                coroutineScope.launch {
                    if (pageIndex < onboardingDataList.size - 1) {
                        pageIndex += 1
                    } else {
                        preferencesManager.setOnboardingSeen()
                        navController.navigate(Routes.SPLASH_SCREEN) {
                            popUpTo(Routes.ONBOARDING_SCREEN) { inclusive = true }
                        }
                    }
                }
            }
        ) {
            Text(text = if (pageIndex == onboardingDataList.size - 1) "Get Started" else "Next")
        }
    }
}
