package com.example.myweather.core.presentation.navigationbar
open class NavigationBarEvent {
    object WeatherTabClick : NavigationBarEvent()
    object EnvironmentDataTabClick : NavigationBarEvent()
    object SettingsTabClick : NavigationBarEvent()
}