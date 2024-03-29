package com.example.myweather.core.presentation.navigationbar.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myweather.feature_settings.presentation.SettingsScreen
import com.example.myweather.core.presentation.util.Screen
import com.example.myweather.feature_environment_data.presentation.EnvironmentDataScreen
import com.example.myweather.feature_weather.presentation.WeatherScreen

@Composable
fun NavigationSetup(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
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