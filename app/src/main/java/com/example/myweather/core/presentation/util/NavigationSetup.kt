package com.example.myweather.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myweather.core.presentation.SettingsScreen
import com.example.myweather.feature_environmental_data.presentation.EnvironmentDataScreen
import com.example.myweather.feature_weather.presentation.weather.WeatherScreen

@Composable
fun NavigationSetup(navController : NavHostController) {
    NavHost(navController = navController, startDestination = Screen.WeatherScreen.route) {
        composable(Screen.WeatherScreen.route) {
            WeatherScreen()
        }
        composable(Screen.EnvironmentDataScreen.route) {
            EnvironmentDataScreen()
        }
        composable(Screen.SettingsScreen.route) {
            SettingsScreen()
        }
    }
}