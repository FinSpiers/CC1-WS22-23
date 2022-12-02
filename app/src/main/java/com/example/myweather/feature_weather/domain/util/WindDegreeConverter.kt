package com.example.myweather.feature_weather.domain.util

class WindDegreeConverter {
    fun convertToDirection(degree: Int): String {
        return when (degree) {
            in 26..64 -> {
                "NE"
            }
            in 65..115 -> {
                "E"
            }
            in 116..154 -> {
                "SE"
            }
            in 155..205 -> {
                "S"
            }
            in 206..244 -> {
                "SW"
            }
            in 245..295 -> {
                "W"
            }
            in 296..334 -> {
                "NW"
            }
            else -> "N"
        }
    }
}