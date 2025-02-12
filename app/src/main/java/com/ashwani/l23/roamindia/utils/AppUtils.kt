package com.ashwani.l23.roamindia.utils
import android.content.Context
import android.content.SharedPreferences

enum class Screens {
    SPLASH_SCREEN, MAIN_SCREEN
}

object Routes {
    const val SPLASH_SCREEN = "SplashScreen"
    const val MAIN_SCREEN = "main"
    const val ONBOARDING_SCREEN = "onboarding"
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

