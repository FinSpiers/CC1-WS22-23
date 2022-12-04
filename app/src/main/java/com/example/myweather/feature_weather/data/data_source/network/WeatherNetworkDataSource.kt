package com.example.myweather.feature_weather.data.data_source.network

import androidx.compose.runtime.MutableState
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather : MutableState<CurrentWeatherData>

    suspend fun fetchCurrentWeather(
        lat : Double,
        lon : Double,
        unit : String,
        language : String
    )
}