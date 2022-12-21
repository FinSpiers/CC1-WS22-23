package com.example.myweather.feature_weather.domain.util

import com.example.myweather.R

object WeatherMainMap {
    private var weatherMainMap : LinkedHashMap<String, Int> = LinkedHashMap()

    init {
        weatherMainMap["Clouds"] = R.drawable.image_weather_clouds
        weatherMainMap["Rain"] = R.drawable.image_weather_rain
        weatherMainMap["Snow"] = R.drawable.image_weather_snow
        weatherMainMap["Extreme"] = R.drawable.image_weather_extreme
        weatherMainMap["Sun"] = R.drawable.image_weather_sunny
        weatherMainMap["Fog"] = R.drawable.image_weather_fog
    }

    fun getWeatherMainMap() : LinkedHashMap<String, Int> {
        return weatherMainMap
    }
}