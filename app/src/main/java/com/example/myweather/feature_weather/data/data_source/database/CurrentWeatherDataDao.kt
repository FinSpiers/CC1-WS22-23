package com.example.myweather.feature_weather.data.data_source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData

@Dao
interface CurrentWeatherDataDao {
    @Transaction
    @Query("SELECT * FROM CurrentWeatherData")
    suspend fun getCurrentWeatherData() : CurrentWeatherData?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCurrentWeatherData(currentWeatherData: CurrentWeatherData)
}