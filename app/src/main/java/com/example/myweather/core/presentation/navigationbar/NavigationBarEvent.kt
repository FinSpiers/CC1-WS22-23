package com.example.myweather.core.presentation

open class NavigationBarEvent {
    object WeatherTabClick : NavigationBarEvent()
    object EnvironmentDataTabClick : NavigationBarEvent()
    object SettingsTabClick : NavigationBarEvent()
}