package com.example.myweather.feature_weather.domain.repository

import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.model.Position

interface WeatherRepository {
    var locationPermissionGranted : Boolean
    var locationPermissionDenied : Boolean
    var lastKnownPosition : Position

    suspend fun getCurrentWeatherDataFromDb() : CurrentWeatherData?

    suspend fun setCurrentWeatherDataInDb(weatherData: CurrentWeatherData)

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
}