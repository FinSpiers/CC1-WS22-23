package com.example.myweather.core.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myweather.core.domain.model.Settings

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings_db")
    suspend fun getSettings() : Settings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSettings(settings: Settings)
}
