package com.example.myweather.core.presentation.navigationbar

import com.example.myweather.core.presentation.util.Screen

data class NavigationBarState(
    val currentRoute: String = Screen.WeatherScreen.route
)
