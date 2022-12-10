package com.example.myweather.core.data.repository

import com.example.myweather.core.data.data_source.SettingsDao
import com.example.myweather.core.domain.model.Settings
import com.example.myweather.core.domain.repository.SettingsRepository

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