package com.example.myweather.feature_weather.domain.use_case

import android.util.Log
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import javax.inject.Inject

class SaveWeatherToDatabaseUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(weatherData : CurrentWeatherData) {
        try {
            repository.setCurrentWeatherDataInDb(weatherData)
        }
        catch (e : Exception) {
            Log.e("DB", "Error on saving weatherdata to db")
        }
    }
}