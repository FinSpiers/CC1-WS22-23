package com.example.myweather.feature_weather.domain.model

import androidx.compose.runtime.MutableState
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myweather.feature_weather.data.data_source.network.response.CurrentWeatherResponse
import com.example.myweather.feature_weather.domain.util.TimestampDatetimeConverter

const val CURRENT_WEATHER_ID = 0

@Entity
data class CurrentWeatherData(
    val dateTime: String = "",
    var location: String = "",
    val isCelsius: Boolean = true,
    val currentTemperature: Double = 0.0,
    val currentWeatherMain: String = "",
    val currentWeatherDescription: String = "",
    val feelsLike: Double = 0.0,
    val airPressure: Int = 0,
    val humidity: Int = 0,
    val minTemp: Double = 0.0,
    val maxTemp: Double = 0.0,
    val windSpeed: Double = 0.0,
    val windDirection: String = ""
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}
