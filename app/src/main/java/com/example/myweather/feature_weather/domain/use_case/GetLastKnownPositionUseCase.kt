package com.example.myweather.feature_weather.domain.use_case

import com.example.myweather.feature_weather.domain.model.Position
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetLastKnownPositionUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke() : Position? {
        return repository.getLastKnownPosition()
    }
}