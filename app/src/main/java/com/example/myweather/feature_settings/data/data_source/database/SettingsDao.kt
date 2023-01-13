package com.example.myweather.feature_settings.data.data_source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myweather.feature_settings.domain.model.Settings

@Dao
interface SettingsDao {

    @Transaction
    @Query("SELECT * FROM settings")
    suspend fun getSettings(): Settings?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSettings(settings: Settings)
}
