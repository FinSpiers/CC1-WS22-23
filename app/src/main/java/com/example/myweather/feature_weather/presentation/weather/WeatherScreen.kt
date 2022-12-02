package com.example.myweather.feature_weather.presentation.weather

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myweather.R
import com.example.myweather.core.domain.Settings
import com.example.myweather.feature_weather.data.data_source.network.OpenWeatherApiService
import com.example.myweather.feature_weather.domain.util.TimestampDatetimeConverter
import com.example.myweather.feature_weather.domain.util.WindDegreeConverter
import com.example.myweather.feature_weather.presentation.weather.components.LocationBar
import com.example.myweather.feature_weather.presentation.weather.components.CurrentWeatherBox
import com.example.myweather.feature_weather.presentation.weather.components.CurrentInformationBox
import com.example.myweather.ui.theme.MyWeatherTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherScreen(
        //viewModel: WeatherViewModel = hiltViewModel()
    ) {
    // TODO: delete later
    val apiService = OpenWeatherApiService()

    val locationName = remember { mutableStateOf("London") }
    val dateTime = remember { mutableStateOf("19.11.2022, 09:05") }

    val currentTemperature = remember { mutableStateOf(-1.5) }
    val feelsLike = remember { mutableStateOf(-2.5) }

    val weatherDescription = remember { mutableStateOf("cloudy") }

    val windSpeed = remember { mutableStateOf(1.54) }
    val windDirection = remember { mutableStateOf("NE") }
    val airPressure = remember { mutableStateOf(1017) }
    val humidity = remember { mutableStateOf(51) }

    val tempMin = remember { mutableStateOf(1.5) }
    val tempMax = remember { mutableStateOf(3.5) }

    GlobalScope.launch(Dispatchers.Main) {
        val currentWeatherResponse = apiService.getCurrentWeatherAsync(lat = 51.517122, lon = 9.417862).await()

        locationName.value = currentWeatherResponse.locationName
        dateTime.value = TimestampDatetimeConverter().convertToDatetime(currentWeatherResponse.dt)
        currentTemperature.value = currentWeatherResponse.main.temp
        feelsLike.value = currentWeatherResponse.main.feelsLike
        weatherDescription.value = currentWeatherResponse.weather[0].description
        windSpeed.value = currentWeatherResponse.wind.speed
        windDirection.value = WindDegreeConverter().convertToDirection(currentWeatherResponse.wind.deg)
        airPressure.value = currentWeatherResponse.main.pressure
        humidity.value = currentWeatherResponse.main.humidity
        tempMin.value = currentWeatherResponse.main.tempMin
        tempMax.value = currentWeatherResponse.main.tempMax
    }
    val isCelsius = Settings().isCelsius
    // TODO: build viewModel with states as above

    MyWeatherTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LocationBar(locationName = locationName.value, dateTime = dateTime.value)
                CurrentWeatherBox(
                    currentTemperature = currentTemperature.value,
                    feelsLike = feelsLike.value,
                    isCelsius = isCelsius,
                    painter = painterResource(id = R.drawable.image_weather_cloudy),
                    weatherDescription = weatherDescription.value
                )

                CurrentInformationBox(
                    isCelsius = isCelsius,
                    minTemperature = tempMin.value,
                    maxTemperature = tempMax.value,
                    airPressure = airPressure.value,
                    humidity = humidity.value,
                    windSpeed = windSpeed.value,
                    windDirection = windDirection.value
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    WeatherScreen()
}