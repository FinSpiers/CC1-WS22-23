package com.example.myweather.feature_weather.domain.use_case

data class WeatherUseCases(
    val getWeatherFromApiUseCase: GetWeatherFromApiUseCase,
    val getWeatherFromDatabaseUseCase: GetWeatherFromDatabaseUseCase,
    val saveWeatherToDatabaseUseCase: SaveWeatherToDatabaseUseCase

)
