package com.roamindia.travel.app.utils

import android.content.Context
import android.content.SharedPreferences

enum class Screens {
    SPLASH_SCREEN, MAIN_SCREEN
}

object Routes {
    const val SPLASH_SCREEN = "SplashScreen"
    const val MAIN_SCREEN = "main"
    const val ONBOARDING_SCREEN = "onboarding"
    const val STATE_SCREEN = "state"
    const val PLACE_SCREEN = "place"
    const val WEATHER_SCREEN = "WeatherPage"

}


class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun setOnboardingSeen() {
        prefs.edit().putBoolean("OnboardingSeen", true).apply()
    }

    fun isOnboardingSeen(): Boolean {
        return prefs.getBoolean("OnboardingSeen", false)
    }
}