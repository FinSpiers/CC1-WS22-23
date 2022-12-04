package com.example.myweather.feature_weather.presentation.weather

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.myweather.R
import com.example.myweather.core.domain.Settings
import com.example.myweather.feature_weather.data.data_source.database.CurrentWeatherDataDao
import com.example.myweather.feature_weather.data.data_source.database.WeatherDatabase
import com.example.myweather.feature_weather.data.data_source.network.OpenWeatherApiService
import com.example.myweather.feature_weather.data.repository.WeatherRepositoryImpl
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
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

    val locationName = remember { mutableStateOf("") }
    val dateTime = remember { mutableStateOf("") }

    val currentTemperature = remember { mutableStateOf(0.0) }
    val feelsLike = remember { mutableStateOf(0.0) }

    val weatherDescription = remember { mutableStateOf("") }

    val windSpeed = remember { mutableStateOf(0.0) }
    val windDirection = remember { mutableStateOf("") }
    val airPressure = remember { mutableStateOf(0) }
    val humidity = remember { mutableStateOf(0) }

    val tempMin = remember { mutableStateOf(0.0) }
    val tempMax = remember { mutableStateOf(0.0) }

    val lat = 51.517122
    val lon = 9.417862


    GlobalScope.launch(Dispatchers.Main) {
        val response = apiService.getCurrentWeatherAsync(lat = lat, lon = lon).await()
        locationName.value = response.locationName
        dateTime.value = TimestampDatetimeConverter.convertToDatetime(response.dt)
        currentTemperature.value = response.main.temp
        feelsLike.value = response.main.feelsLike
        weatherDescription.value = response.weather[0].description
        windSpeed.value = response.wind.speed
        windDirection.value = WindDegreeConverter.convertToDirection(response.wind.deg)
        airPressure.value = response.main.pressure
        humidity.value = response.main.humidity
        tempMin.value = response.main.tempMin
        tempMax.value = response.main.tempMax
    }
    val isCelsius = Settings().isCelsius
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