package com.example.myweather.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myweather.feature_settings.data.data_source.database.SettingsDao
import com.example.myweather.feature_settings.domain.model.Settings
import com.example.myweather.feature_weather.data.data_source.database.CurrentWeatherDataDao
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData

@Database(
    entities = [
        CurrentWeatherData::class,
        Settings::class
    ],
    version = 1
)
abstract class MyWeatherDatabase : RoomDatabase() {
    abstract val currentWeatherDao : CurrentWeatherDataDao

    abstract val settingsDao : SettingsDao
}