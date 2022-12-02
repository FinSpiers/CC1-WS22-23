package com.example.myweather.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myweather.core.presentation.util.Screen

sealed class BottomNavItem(
    val index : Int,
    val route : String,
    val title : String,
    val icon : ImageVector
) {
    object Weather : BottomNavItem(0, Screen.WeatherScreen.route, "Weather", Icons.Default.Cloud)
    object EnvironmentData : BottomNavItem(1, Screen.EnvironmentDataScreen.route, "Environment data", Icons.Default.Details)
    object Settings : BottomNavItem(2, Screen.SettingsScreen.route, "Settings", Icons.Default.Settings)
}
