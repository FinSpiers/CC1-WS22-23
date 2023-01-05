package com.example.myweather.feature_weather.presentation

sealed class WeatherEvent {
    object UpdateCurrentWeatherData : WeatherEvent()
    object NoNetworkConnection : WeatherEvent()
    object OnLocationChange : WeatherEvent()
}
