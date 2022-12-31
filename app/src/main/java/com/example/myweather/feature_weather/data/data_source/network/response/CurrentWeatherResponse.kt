package com.example.myweather.feature_weather.data.data_source.network.response

import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

data class CurrentWeatherResponse(
    val coord: Coord,
    val cod: Int,
    val dt: Int,
    val main: Main,

    @SerializedName("name")
    val locationName: String,
    val weather: List<Weather>,
    val wind: Wind,
    val timezone : Int
)

fun CurrentWeatherResponse.toCurrentWeatherData() : CurrentWeatherData {
    return CurrentWeatherData(
        lat = coord.lat,
        lon = coord.lon,
        timeStamp = (dt + timezone).toLong(),
        location = locationName,
        isCelsius = true,
        currentTemperature = main.temp.roundToInt(),
        currentWeatherMain = weather[0].main,
        currentWeatherDescription = weather[0].description,
        feelsLike = main.feelsLike.roundToInt(),
        airPressure = main.pressure,
        humidity = main.humidity,
        minTemp = main.tempMin.roundToInt(),
        maxTemp = main.tempMax.roundToInt(),
        windSpeed = wind.speed,
        windDeg = wind.deg
    )
}