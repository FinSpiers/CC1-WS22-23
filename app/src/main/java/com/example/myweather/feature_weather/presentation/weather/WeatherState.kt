package com.example.myweather.feature_weather.presentation.weather

import com.example.myweather.feature_weather.data.data_source.network.response.Sys
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData

data class WeatherState(
    val timeStamp : Long = System.currentTimeMillis(),
    var weatherData : CurrentWeatherData? = CurrentWeatherData()
    )
