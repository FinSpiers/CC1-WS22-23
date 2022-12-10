package com.example.myweather.core.domain.repository

import com.example.myweather.core.domain.model.Settings

interface SettingsRepository {
    suspend fun getSettingsFromDatabase() : Settings?

    suspend fun saveSettingsToDatabase(settings: Settings)
}