package com.example.myweather.core.presentation.util

// Map destinations and routes for navigation
sealed class Screen(val route: String) {
    object WeatherScreen : Screen("weather_screen")
    object EnvironmentDataScreen : Screen("environment_data_screen")
    object SettingsScreen : Screen("settings_screen")
}
