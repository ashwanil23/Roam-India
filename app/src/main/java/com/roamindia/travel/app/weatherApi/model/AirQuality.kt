package com.roamindia.travel.app.weatherApi.model

import androidx.annotation.DrawableRes

data class AirQuality(
    val co: String,
    val no2: String,
    val o3: String,
    val so2: String,
    val pm25: String,
    val pm10: String,
)