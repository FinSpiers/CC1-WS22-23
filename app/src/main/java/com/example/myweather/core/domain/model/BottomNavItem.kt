package com.example.myweather.core.domain.model

import androidx.compose.ui.graphics.painter.Painter

sealed class BottomNavItem(
    val index: Int,
    val route: String,
    val title: String,
    val icon: Painter
) {
    companion object {
        class Weather(index: Int, route: String, title: String, icon: Painter) :
            BottomNavItem(index, route, title, icon)

        class EnvironmentData(index: Int, route: String, title: String, icon: Painter) :
            BottomNavItem(index, route, title, icon)

        class Settings(index: Int, route: String, title: String, icon: Painter) :
            BottomNavItem(index, route, title, icon)
    }
}
