package com.example.myweather.feature_weather.domain.repository

import com.example.myweather.feature_weather.data.data_source.network.response.CurrentWeatherResponse
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import kotlinx.coroutines.Deferred

interface WeatherRepository {
    suspend fun getCurrentWeatherDataFromDb() : CurrentWeatherData?

    suspend fun setCurrentWeatherDataInDb(weatherData: CurrentWeatherData)

    suspend fun getCurrentWeatherAsync(
        lat: Double,
        lon: Double,
        unit: String,
        language: String
    ): Deferred<CurrentWeatherResponse>
}