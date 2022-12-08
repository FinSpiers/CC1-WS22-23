package com.example.myweather.feature_weather.domain.util

import java.time.*

object TimestampDatetimeConverter {
    fun convertToDatetime(timestamp: Long) : String {
        var dateTime = ""

        val dt = Instant.ofEpochSecond(timestamp.toLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        dt.toLocalDate()
            .toString()
            .split('-')
            .asReversed()
            .forEach { str -> dateTime += "$str." }

        dateTime = dateTime.dropLast(1)
        dateTime += ", ${dt.toLocalTime()}"
        return dateTime
    }
}