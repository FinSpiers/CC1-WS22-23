package com.example.myweather.feature_weather.data.repository

import android.content.Context
import com.example.myweather.feature_weather.data.data_source.database.CurrentWeatherDataDao
import com.example.myweather.feature_weather.data.data_source.network.OpenWeatherApiService
import com.example.myweather.feature_weather.data.data_source.network.response.toCurrentWeatherData
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.model.Position
import com.example.myweather.feature_weather.domain.repository.WeatherRepository

class WeatherRepositoryImpl (
     private val dao: CurrentWeatherDataDao,
     private val apiService: OpenWeatherApiService,
     override val context : Context
 ) : WeatherRepository {
    // Create boolean vars for permission granted and denied which are set to false initially
    override var locationPermissionGranted: Boolean = false
    override var locationPermissionDenied: Boolean = false

    // Create var for the last known position
    override var lastKnownPosition: Position = Position(0.0, 0.0)

    override suspend fun getCurrentWeatherDataFromDb(): CurrentWeatherData? {
         return dao.getCurrentWeatherData()
     }

     override suspend fun setCurrentWeatherDataInDb(weatherData: CurrentWeatherData) {
         return dao.setCurrentWeatherData(weatherData)
     }

    // Function to get current weather data by api service and parse it to currentWeatherData object
     override suspend fun getCurrentWeatherAsync(lat : Double, lon : Double, unit : String, language : String) : CurrentWeatherData {
         return apiService.getCurrentWeatherAsync(lat, lon, unit, language).await().toCurrentWeatherData()
     }

    override suspend fun getLastKnownPosition(): Position {
        return lastKnownPosition
    }

    override suspend fun setLastKnownPosition(value: Position) {
        lastKnownPosition = value
    }

    override fun isLocationPermissionGranted(): Boolean {
        return locationPermissionGranted
    }

    override suspend fun setLocationPermissionGranted(value: Boolean) {
        locationPermissionGranted = value
    }

    override fun isLocationPermissionDenied(): Boolean {
        return locationPermissionDenied
    }

    override suspend fun setLocationPermissionDenied(value: Boolean) {
        locationPermissionDenied = value
    }

    override fun getContextInstance(): Context {
        return context
    }
}