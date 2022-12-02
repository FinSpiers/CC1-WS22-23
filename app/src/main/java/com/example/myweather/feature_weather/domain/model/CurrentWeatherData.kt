package com.example.myweather.feature_weather.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myweather.feature_weather.data.data_source.network.response.CurrentWeatherResponse
import com.example.myweather.feature_weather.domain.util.TimestampDatetimeConverter

const val CURRENT_WEATHER_ID = 0

@Entity
data class CurrentWeatherData(
    val dateTime : String = "01.01.2000",
    val location : String = "Default",
    val isCelsius : Boolean = true,
    val currentTemperature : Double = 0.0,
    val currentWeatherMain : String = "Rain",
    val currentWeatherDescription : String = "Moderate rain",
    val feelsLike : Double = 0.0,
    val airPressure : Int = 1,
    val humidity : Int = 1,
    val minTemp : Double = 0.0,
    val maxTemp : Double = 0.0,
    val windSpeed : Double = 0.0,
    val windDirection : String = "Default"
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}
