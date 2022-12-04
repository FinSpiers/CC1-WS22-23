package com.example.myweather.feature_weather.presentation.weather

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.R
import com.example.myweather.core.domain.Settings
import com.example.myweather.feature_weather.data.data_source.network.interceptor.ConnectivityInterceptorImpl
import com.example.myweather.feature_weather.data.data_source.network.OpenWeatherApiService
import com.example.myweather.feature_weather.data.data_source.network.WeatherNetworkDataSourceImpl
import com.example.myweather.feature_weather.presentation.weather.components.LocationBar
import com.example.myweather.feature_weather.presentation.weather.components.CurrentWeatherBox
import com.example.myweather.feature_weather.presentation.weather.components.CurrentInformationBox
import com.example.myweather.ui.theme.MyWeatherTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherScreen(
    //viewModel: WeatherViewModel = hiltViewModel()
) {
    // TODO: delete later
    val apiService =
        OpenWeatherApiService(ConnectivityInterceptorImpl(LocalContext.current))
    val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
    val currentWeatherData = remember { weatherNetworkDataSource.downloadedCurrentWeather }

    val lat = 51.517122
    val lon = 9.417862
    val unit = "metric"
    val language = "en"
    val isCelsius = Settings().isCelsius


    GlobalScope.launch(Dispatchers.Main) {
        weatherNetworkDataSource.fetchCurrentWeather(lat, lon, unit, language)
    }

    // TODO: build viewModel with states as above
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
                    locationName = currentWeatherData.value.location,
                    dateTime = currentWeatherData.value.dateTime
                )
                CurrentWeatherBox(
                    currentTemperature = currentWeatherData.value.currentTemperature,
                    feelsLike = currentWeatherData.value.feelsLike,
                    isCelsius = currentWeatherData.value.isCelsius,
                    painter = painterResource(id = R.drawable.image_weather_cloudy),
                    weatherDescription = currentWeatherData.value.currentWeatherDescription
                )

                CurrentInformationBox(
                    isCelsius = isCelsius,
                    minTemperature = currentWeatherData.value.minTemp,
                    maxTemperature = currentWeatherData.value.maxTemp,
                    airPressure = currentWeatherData.value.airPressure,
                    humidity = currentWeatherData.value.humidity,
                    windSpeed = currentWeatherData.value.windSpeed,
                    windDirection = currentWeatherData.value.windDirection
                )
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    WeatherScreen()
}