package com.example.myweather.feature_weather.domain.repository

import com.example.myweather.feature_weather.domain.model.CurrentWeatherData

interface WeatherRepository {
    suspend fun getCurrentWeatherData() : CurrentWeatherData?

    suspend fun setCurrentWeatherData(weatherData: CurrentWeatherData)

}