package com.ashwani.l23.roamindia.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class OnboardingData(
    val description: String,
    @DrawableRes val imageRes: Int,
    val color: Color
)
