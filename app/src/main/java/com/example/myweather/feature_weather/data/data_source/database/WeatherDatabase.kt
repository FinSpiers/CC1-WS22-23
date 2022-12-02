package com.example.myweather.feature_weather.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData

@Database(
    entities = [CurrentWeatherData::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val weatherDataDao: CurrentWeatherDataDao

    companion object {
        const val DATABASE_NAME = "weather_db"
    }
}