package com.example.myweather.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myweather.core.data.data_source.SettingsDatabase

const val CURRENT_SETTINGS_ID = 0

@Entity(tableName = SettingsDatabase.DATABASE_NAME)
class Settings(
    var isCelsius: Boolean = true
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_SETTINGS_ID
}