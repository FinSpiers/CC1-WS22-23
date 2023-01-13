package com.example.myweather.feature_settings.presentation

import com.example.myweather.feature_settings.domain.model.Settings

data class SettingsState(
    val isCelsius : Boolean = true,
    val settings: Settings = Settings(isCelsius = isCelsius)
    )
