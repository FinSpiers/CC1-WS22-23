package com.example.myweather.feature_weather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.R
import com.example.myweather.feature_weather.presentation.weather.components.LocationBar
import com.example.myweather.feature_weather.presentation.weather.components.WeatherHourlyBar
import com.example.myweather.feature_weather.presentation.weather.components.WeatherInfoBox
import com.example.myweather.ui.theme.MyWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
    @Composable
    fun MyApp() {
        val weatherData = listOf(1.0, 2.0, -1.5, 3.5, 1.0, 2.0, -1.5, 3.5, 1.0, 2.0, -1.5, 3.5, 1.0, 2.0, -1.5, 3.5, 1.0, 2.0, -1.5, 3.5, 1.0, 2.0, -1.5, 3.5)
        MyWeatherTheme {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LocationBar(
                    locationName = "London",
                    dateTime = "19.11.2022, 09:05"
                )

                WeatherInfoBox(
                    currentTemperature = -1.5,
                    isCelsius = true,
                    painter = painterResource(id = R.drawable.image_weather_cloudy),
                    weatherDescription = "No description available yet"
                )
                Spacer(modifier = Modifier.height(32.dp))
                WeatherHourlyBar(weatherData = weatherData, isCelsius = true)
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MyApp()
    }
}