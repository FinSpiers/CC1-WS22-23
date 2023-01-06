package com.example.myweather.feature_weather.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myweather.R
import com.example.myweather.feature_weather.presentation.permissions.RequestPermissions
import com.example.myweather.feature_weather.domain.util.TimestampDatetimeConverter
import com.example.myweather.feature_weather.domain.util.WeatherMainMap
import com.example.myweather.feature_weather.domain.util.WindDegreeConverter
import com.example.myweather.feature_weather.presentation.components.LocationBar
import com.example.myweather.feature_weather.presentation.components.CurrentWeatherBox
import com.example.myweather.feature_weather.presentation.components.CurrentInformationBox
import com.example.myweather.ui.theme.MyWeatherTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    // Create and remember scrollState
    val scrollState = rememberScrollState(0)

    // Get the state by the viewModel
    val state = viewModel.state.value

    // Get isLoading by viewModel as state
    val isLoading by viewModel.isLoading.collectAsState()

    // Create and remember swipeRefreshState with isRefreshing = onLoading
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    // Define a painter for each case the weather main description could be
    val painter: Painter = if (state.weatherData != null && WeatherMainMap.getWeatherMainMap()
            .containsKey(state.weatherData?.currentWeatherMain)
    ) {
        val id = WeatherMainMap.getWeatherMainMap()[state.weatherData?.currentWeatherMain]
        painterResource(id = id!!)
    } else {
        painterResource(id = R.drawable.image_weather_sunny_with_clouds)
    }

    // Check permission request
    RequestPermissions(
        setLocationPermissionGranted = { viewModel.setLocationPermissionGranted() },
        setLocationPermissionDenied = { viewModel.setLocationPermissionDenied() }
    )

    MyWeatherTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { viewModel.onEvent(WeatherEvent.UpdateCurrentWeatherData) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LocationBar(
                        locationName = state.weatherData?.location,
                        // Use TimestampConverter to convert timestamp into dateTime
                        dateTime = state.weatherData?.let {
                            TimestampDatetimeConverter.convertToDatetime(
                                it.timeStamp
                            ).dropLast(3)
                        }
                    )
                    state.weatherData?.let {
                        CurrentWeatherBox(
                            currentTemperature = it.currentTemperature,
                            feelsLike = it.feelsLike,
                            isCelsius = state.isCelsius,
                            painter = painter,
                            weatherDescription = it.currentWeatherDescription
                        )
                    }

                    state.weatherData?.let {
                        CurrentInformationBox(
                            isCelsius = state.isCelsius,
                            minTemperature = it.minTemp,
                            maxTemperature = it.maxTemp,
                            airPressure = it.airPressure,
                            humidity = it.humidity,
                            windSpeed = it.windSpeed,
                            // Make use of the windDegreeConverter
                            windDirection = WindDegreeConverter(LocalContext.current).convertToDirection(it.windDeg)
                        )
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }

        }
    }
}