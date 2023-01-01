package com.example.myweather.feature_settings.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_SETTINGS_ID = 0

@Entity(tableName = "settings")
class Settings(
    var isCelsius: Boolean = true
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_SETTINGS_ID
}