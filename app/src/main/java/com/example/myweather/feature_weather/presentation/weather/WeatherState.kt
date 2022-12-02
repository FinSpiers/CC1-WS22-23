package com.example.myweather.feature_weather.presentation.weather

import com.example.myweather.feature_weather.domain.model.CurrentWeatherData

data class WeatherState(
    val timeStamp : Long = 0,
    var weatherData : CurrentWeatherData = CurrentWeatherData()
    )
