package com.roamindia.travel.app.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.roamindia.travel.app.R
import com.roamindia.travel.app.utils.PreferencesManager
import com.roamindia.travel.app.utils.Routes
import com.roamindia.travel.app.viewModel.OnboardingViewModel
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    navController: NavController,
    preferencesManager: PreferencesManager,
    viewModel: OnboardingViewModel = viewModel()
) {
    val onboardingDataUiState by viewModel.onboardingData.collectAsState()
    val currentStepUiState by viewModel.currentStep.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .background(color = onboardingDataUiState.onboardingDataListItems.getOrNull(currentStepUiState)?.color ?: Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        onboardingDataUiState.onboardingDataListItems.getOrNull(currentStepUiState)?.let { page ->
            Image(
                painter = painterResource(page.imageRes),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Text(text = page.description, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            onboardingDataUiState.onboardingDataListItems.indices.forEach { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            if (currentStepUiState == index)
                                colorResource(R.color.parrot_button)
                            else
                                Color.Gray, CircleShape)
                )
                if (index != onboardingDataUiState.onboardingDataListItems.lastIndex) {
                    Spacer(Modifier.width(8.dp))
                }
            }
        }
        Button(
            colors = ButtonDefaults.buttonColors(colorResource(R.color.parrot_button)),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                coroutineScope.launch {
                    if (currentStepUiState < onboardingDataUiState.onboardingDataListItems.size - 1) {
                        viewModel.nextStep()
                    } else {
                        viewModel.completeOnboarding()
                        preferencesManager.setOnboardingSeen()
                        navController.navigate(Routes.SPLASH_SCREEN) {
                            popUpTo(Routes.ONBOARDING_SCREEN) { inclusive = true }
                        }
                    }
                }
            }
        ) {
            Text(
                text = if (currentStepUiState == onboardingDataUiState.onboardingDataListItems.size - 1)
                    "Get Started" else "Next")
        }
    }
}
