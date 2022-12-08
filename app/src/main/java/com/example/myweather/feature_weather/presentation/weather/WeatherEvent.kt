package com.example.myweather.feature_weather.presentation.weather

import com.example.myweather.feature_weather.domain.model.CurrentWeatherData

sealed class WeatherEvent {
    data class UpdateCurrentWeatherData(val weatherData: CurrentWeatherData) : WeatherEvent()
    object RequestLocationPermission : WeatherEvent()
    object NoNetworkConnection : WeatherEvent()
}
