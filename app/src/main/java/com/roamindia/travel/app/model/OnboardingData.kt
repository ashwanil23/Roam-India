package com.roamindia.travel.app.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class OnboardingData(
    val description: String,
    @DrawableRes val imageRes: Int,
    val color: Color
)

