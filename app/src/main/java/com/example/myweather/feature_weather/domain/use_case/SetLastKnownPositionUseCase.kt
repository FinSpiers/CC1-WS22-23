package com.example.myweather.feature_weather.domain.use_case

import com.example.myweather.feature_weather.domain.model.Position
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import javax.inject.Inject

class SetLastKnownPositionUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(value : Position) {
        repository.setLastKnownPosition(value)
    }
}