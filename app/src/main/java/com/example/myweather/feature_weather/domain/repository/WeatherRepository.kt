package com.example.myweather.feature_weather.domain.repository

import android.content.Context
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.model.Position

interface WeatherRepository {
    // Create boolean vars for permission granted and denied
    var locationPermissionGranted : Boolean
    var locationPermissionDenied : Boolean

    // Create val for context instance
    val context : Context

    // Create var for the last known position
    var lastKnownPosition : Position

    suspend fun getCurrentWeatherDataFromDb() : CurrentWeatherData?

    suspend fun setCurrentWeatherDataInDb(weatherData: CurrentWeatherData)

    // Function to request the current weather data from api for the given arguments
    suspend fun getCurrentWeatherAsync(
        lat: Double,
        lon: Double,
        unit: String,
        language: String
    ): CurrentWeatherData?

    suspend fun getLastKnownPosition() : Position?

    suspend fun setLastKnownPosition(value : Position)

    fun isLocationPermissionGranted() : Boolean

    suspend fun setLocationPermissionGranted(value : Boolean)

    fun isLocationPermissionDenied() : Boolean

    suspend fun setLocationPermissionDenied(value : Boolean)

    fun getContextInstance() : Context
}
