package com.example.myweather.feature_weather.presentation

import com.example.myweather.feature_weather.domain.model.CurrentWeatherData

data class WeatherState(
    val timeStamp: Long = System.currentTimeMillis(),
    var isCelsius: Boolean = true,
    var weatherData: CurrentWeatherData? = null
)
