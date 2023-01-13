package com.example.myweather.feature_weather.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myweather.core.domain.util.CelsiusFahrenheitConverter

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "currentWeatherData")
data class CurrentWeatherData(
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var timeStamp: Long = 0,
    var location: String = "",
    var isCelsius: Boolean = true,
    var currentTemperature: Int = 0,
    var currentWeatherMain: String = "",
    var currentWeatherDescription: String = "",
    var feelsLike: Int = 0,
    var airPressure: Int = 0,
    var humidity: Int = 0,
    var minTemp: Int = 0,
    var maxTemp: Int = 0,
    var windSpeed: Double = 0.0,
    var windDeg: Int = 0
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID

    fun convertToFahrenheit(): CurrentWeatherData {
        if (isCelsius) {
            currentTemperature = CelsiusFahrenheitConverter.convertToFahrenheit(currentTemperature)
            feelsLike = CelsiusFahrenheitConverter.convertToFahrenheit(feelsLike)
            minTemp = CelsiusFahrenheitConverter.convertToFahrenheit(minTemp)
            maxTemp = CelsiusFahrenheitConverter.convertToFahrenheit(maxTemp)
            isCelsius = false
        }
        return this
    }

    fun convertToCelsius(): CurrentWeatherData {
        if (!isCelsius) {
            currentTemperature = CelsiusFahrenheitConverter.convertToCelsius(currentTemperature)
            feelsLike = CelsiusFahrenheitConverter.convertToCelsius(feelsLike)
            minTemp = CelsiusFahrenheitConverter.convertToCelsius(minTemp)
            maxTemp = CelsiusFahrenheitConverter.convertToCelsius(maxTemp)
            isCelsius = true
        }
        return this
    }
}
