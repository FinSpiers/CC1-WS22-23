package com.example.myweather.feature_weather.domain.use_case

import com.example.myweather.feature_weather.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke() {
        repository.getCurrentWeatherDataFromDb()
    }
}