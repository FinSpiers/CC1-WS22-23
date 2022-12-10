package com.example.myweather.feature_weather.presentation.weather

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myweather.R
import com.example.myweather.core.presentation.MainActivity
import com.example.myweather.feature_weather.domain.util.TimestampDatetimeConverter
import com.example.myweather.feature_weather.domain.util.WindDegreeConverter
import com.example.myweather.feature_weather.presentation.weather.components.LocationBar
import com.example.myweather.feature_weather.presentation.weather.components.CurrentWeatherBox
import com.example.myweather.feature_weather.presentation.weather.components.CurrentInformationBox
import com.example.myweather.ui.theme.MyWeatherTheme


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherScreen(
    context: Context,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scrollState = rememberScrollState(0)
    MyWeatherTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LocationBar(
                    locationName = state.weatherData?.location,
                    dateTime = state.weatherData?.let {
                        TimestampDatetimeConverter.convertToDatetime(
                            it.timeStamp)
                    }
                )
                state.weatherData?.let {
                    CurrentWeatherBox(
                        currentTemperature = it.currentTemperature,
                        feelsLike = it.feelsLike,
                        isCelsius = it.isCelsius,
                        painter = painterResource(id = R.drawable.image_weather_cloudy),
                        weatherDescription = it.currentWeatherDescription
                    )
                }

                state.weatherData?.let {
                    CurrentInformationBox(
                        // TODO: set dynamic
                        isCelsius = true,
                        minTemperature = it.minTemp,
                        maxTemperature = it.maxTemp,
                        airPressure = it.airPressure,
                        humidity = it.humidity,
                        windSpeed = it.windSpeed,
                        windDirection = WindDegreeConverter.convertToDirection(it.windDeg, context)
                    )
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}