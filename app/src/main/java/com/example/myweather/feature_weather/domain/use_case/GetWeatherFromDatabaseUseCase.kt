package com.example.myweather.feature_weather.domain.use_case

import android.util.Log
import com.example.myweather.feature_weather.data.data_source.network.NoConnectivityException
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherFromDatabaseUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke() : CurrentWeatherData? {
        return try {
            repository.getCurrentWeatherDataFromDb()
        } catch (e : Exception) {
            Log.e("DB", "Error on loading weather data from db")
            null
        }
    }

}