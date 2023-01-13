package com.example.myweather.feature_weather.domain.util

import java.time.*

// Helper object that provides a function to convert an utc timestamp into a formatted dateTime string
object TimestampDatetimeConverter {
    fun convertToDatetime(timestamp: Long): String {
        var dateTime = ""

        val dt = Instant.ofEpochSecond(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        dt.toLocalDate()
            .toString()
            .split('-')
            .asReversed()
            .forEach { str -> dateTime += "$str." }

        dateTime = dateTime.dropLast(1)
        dateTime += ", ${dt.toLocalTime().toString().dropLast(3)}"
        return dateTime
    }
}