package com.example.myweather.feature_weather.presentation.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myweather.R
import com.example.myweather.feature_weather.presentation.weather.components.LocationBar
import com.example.myweather.feature_weather.presentation.weather.components.CurrentWeatherBox
import com.example.myweather.feature_weather.presentation.weather.components.WeatherTodayInfoBox
import com.example.myweather.ui.theme.MyWeatherTheme

@Composable
fun WeatherScreen() {
    val weatherData = listOf(
        1.0,
        2.0,
        -1.5,
        3.5,
        1.0,
        2.0,
        -1.5,
        3.5,
        1.0,
        2.0,
        -1.5,
        3.5,
        1.0,
        2.0,
        -1.5,
        3.5,
        1.0,
        2.0,
        -1.5,
        3.5,
        1.0,
        2.0,
        -1.5,
        3.5
    )
    MyWeatherTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LocationBar(
                    locationName = "London",
                    dateTime = "19.11.2022, 09:05"
                )

                CurrentWeatherBox(
                    currentTemperature = -1.5,
                    feelsLike = -2.5,
                    isCelsius = true,
                    painter = painterResource(id = R.drawable.image_weather_cloudy),
                    weatherDescription = "No description available yet"
                )
                Spacer(modifier = Modifier.height(16.dp))
                WeatherTodayInfoBox(
                    weatherData = weatherData,
                    isCelsius = true,
                    dailyMinTemperature = -6.0,
                    dailyMaxTemperature = 1.5
                )
            }
        }
    }

}