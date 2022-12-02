package com.example.myweather.feature_weather.data.data_source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData

@Dao
interface CurrentWeatherDataDao {
    @Query("SELECT * FROM currentweatherdata")
    suspend fun getCurrentWeatherData() : CurrentWeatherData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCurrentWeatherData(weatherData: CurrentWeatherData)
}