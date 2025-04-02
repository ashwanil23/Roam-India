package com.roamindia.travel.app.model

import androidx.compose.ui.graphics.Color
import com.roamindia.travel.app.R

object OnboardingDataRes {
    val onboardingDataList = listOf(
        OnboardingData(
            description = "Welcome to Roam India!",
            imageRes = R.drawable.first,
            color = Color(0xFF709E97)
        ),
        OnboardingData(
            description = "Discover amazing places.",
            imageRes = R.drawable.second,
            color = Color(0xFFCECBB5)
        ),
        OnboardingData(
            description = "Plan your perfect trip.",
            imageRes = R.drawable.third,
            color = Color(0xFF769C8F)
        )
    )
}