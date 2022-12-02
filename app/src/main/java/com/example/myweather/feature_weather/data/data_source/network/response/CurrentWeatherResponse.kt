package com.example.myweather.feature_weather.data.data_source.network.response

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val cod: Int,
    val dt: Int,
    val main: Main,
    @SerializedName("name")
    val locationName: String,
    val weather: List<Weather>,
    val wind: Wind
)