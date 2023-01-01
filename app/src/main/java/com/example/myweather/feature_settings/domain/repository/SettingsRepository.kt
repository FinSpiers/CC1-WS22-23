package com.example.myweather.feature_settings.domain.repository

import com.example.myweather.feature_settings.domain.model.Settings

interface SettingsRepository {
    suspend fun getSettingsFromDatabase() : Settings?

    suspend fun saveSettingsToDatabase(settings: Settings)
}