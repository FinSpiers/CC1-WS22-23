package com.example.myweather.feature_settings.data.repository

import com.example.myweather.feature_settings.data.data_source.database.SettingsDao
import com.example.myweather.feature_settings.domain.model.Settings
import com.example.myweather.feature_settings.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsDao: SettingsDao
) : SettingsRepository {

    // Function to get the current settings from the database
    override suspend fun getSettingsFromDatabase(): Settings? {
        return settingsDao.getSettings()
    }

    // Function to set the current settings in the database
    override suspend fun saveSettingsToDatabase(settings: Settings) {
        settingsDao.setSettings(settings)
    }
}