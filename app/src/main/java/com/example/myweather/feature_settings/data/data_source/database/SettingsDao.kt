package com.example.myweather.feature_settings.data.data_source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myweather.feature_settings.domain.model.Settings

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings")
    suspend fun getSettings() : Settings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSettings(settings: Settings)
}
