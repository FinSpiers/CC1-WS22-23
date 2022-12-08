package com.example.myweather.feature_weather.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_WEATHER_ID = 0

@Entity
data class CurrentWeatherData(
    val timeStamp: Long = 0,
    var location: String = "Test",
    val isCelsius: Boolean = true,
    val currentTemperature: Double = 0.0,
    val currentWeatherMain: String = "",
    val currentWeatherDescription: String = "Test",
    val feelsLike: Double = 0.0,
    val airPressure: Int = 0,
    val humidity: Int = 0,
    val minTemp: Double = 0.0,
    val maxTemp: Double = 0.0,
    val windSpeed: Double = 0.0,
    val windDeg: Int = 0
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}
