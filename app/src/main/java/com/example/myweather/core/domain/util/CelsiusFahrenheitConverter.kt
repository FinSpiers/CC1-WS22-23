package com.example.myweather.core.domain.util

import kotlin.math.roundToInt

object CelsiusFahrenheitConverter {
    fun convertToFahrenheit(tempCelsius : Int) : Int {
        return (tempCelsius * 1.8  + 32).roundToInt()
    }
    fun convertToCelsius(tempFahrenheit : Int)  : Int {
        return ((tempFahrenheit - 32) * 5/9)
    }
}