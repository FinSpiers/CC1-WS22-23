package com.example.myweather.feature_weather.data.data_source.network.response


data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)