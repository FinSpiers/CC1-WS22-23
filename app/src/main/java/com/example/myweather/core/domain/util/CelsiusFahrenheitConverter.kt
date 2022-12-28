package com.example.myweather.core.domain.util

object CelsiusFahrenheitConverter {
    fun convertToFahrenheit(tempCelsius : Double) : Double {
        return (tempCelsius * 1.8  + 32)
    }
    fun convertToCelsius(tempFahrenheit : Double)  : Double {
        return (tempFahrenheit - 32) * 5/9
    }
}