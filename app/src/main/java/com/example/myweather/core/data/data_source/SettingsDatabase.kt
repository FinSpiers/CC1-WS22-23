package com.example.myweather.core.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myweather.core.domain.model.Settings

@Database(
    entities = [Settings::class],
    version = 1
)
abstract class SettingsDatabase : RoomDatabase() {

    abstract fun settingsDao(): SettingsDao

    companion object {
        const val DATABASE_NAME = "settings_db"
    }
}