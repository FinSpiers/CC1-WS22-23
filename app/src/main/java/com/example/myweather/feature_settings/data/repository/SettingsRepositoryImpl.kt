package com.example.myweather.feature_settings.data.repository

import com.example.myweather.feature_settings.data.data_source.database.SettingsDao
import com.example.myweather.feature_settings.domain.model.Settings
import com.example.myweather.feature_settings.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsDao: SettingsDao
    ) : SettingsRepository {
    override suspend fun getSettingsFromDatabase(): Settings? {
        return settingsDao.getSettings()
    }

    override suspend fun saveSettingsToDatabase(settings: Settings) {
        settingsDao.setSettings(settings)
    }
}