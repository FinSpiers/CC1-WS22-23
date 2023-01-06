package com.example.myweather.feature_weather.domain.util

import com.example.myweather.R

// Helper object that provides a static hashMap that maps the weather main descriptions and the
// ID's of the image that should be displayed
object WeatherMainMap {
    private var weatherMainMap : LinkedHashMap<String, Int> = LinkedHashMap()

    init {
        weatherMainMap["Clouds"] = R.drawable.image_weather_clouds
        weatherMainMap["Rain"] = R.drawable.image_weather_rain
        weatherMainMap["Snow"] = R.drawable.image_weather_snow
        weatherMainMap["Extreme"] = R.drawable.image_weather_extreme
        weatherMainMap["Sun"] = R.drawable.image_weather_sunny
        weatherMainMap["Fog"] = R.drawable.image_weather_fog
        weatherMainMap["Clear"] = R.drawable.image_weather_sunny
        weatherMainMap["Drizzle"] = R.drawable.image_weather_drizzle
    }

    // Function to get the hashmap
    fun getWeatherMainMap() : LinkedHashMap<String, Int> {
        return weatherMainMap
    }
}