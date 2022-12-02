package com.example.myweather

import android.util.Log
import com.example.myweather.feature_weather.data.data_source.OpenWeatherApiService
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    suspend fun getCurrentWeatherTest() {
        val lat = 51.517122
        val lon = 9.417862
        val currentWeather = OpenWeatherApiService().getCurrentWeatherAsync(lat, lon).await()
        Log.e("Test", currentWeather.toString())
    }
}