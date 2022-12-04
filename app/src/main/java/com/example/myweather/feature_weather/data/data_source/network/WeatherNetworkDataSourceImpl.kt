package com.example.myweather.feature_weather.data.data_source.network

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.util.TimestampDatetimeConverter
import com.example.myweather.feature_weather.domain.util.WindDegreeConverter
import kotlinx.coroutines.flow.MutableStateFlow

class WeatherNetworkDataSourceImpl(
    private val openWeatherApiService: OpenWeatherApiService,
    private val context: Context
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = mutableStateOf(CurrentWeatherData())

    override val downloadedCurrentWeather: MutableState<CurrentWeatherData>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        language: String
    ) {
        try {
            val fetchedCurrentWeatherData = openWeatherApiService
                .getCurrentWeatherAsync(lat, lon, unit, language)
                .await()

            _downloadedCurrentWeather.value = CurrentWeatherData(
                dateTime = TimestampDatetimeConverter.convertToDatetime(fetchedCurrentWeatherData.dt),
                location = fetchedCurrentWeatherData.locationName,
                // TODO: change
                isCelsius = true,
                currentTemperature = fetchedCurrentWeatherData.main.temp,
                currentWeatherMain = fetchedCurrentWeatherData.weather[0].main,
                currentWeatherDescription = fetchedCurrentWeatherData.weather[0].description,
                feelsLike = fetchedCurrentWeatherData.main.feelsLike,
                airPressure = fetchedCurrentWeatherData.main.pressure,
                humidity = fetchedCurrentWeatherData.main.humidity,
                windSpeed = fetchedCurrentWeatherData.wind.speed,
                windDirection = WindDegreeConverter.convertToDirection(
                    fetchedCurrentWeatherData.wind.deg,
                    context
                )
            )
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
        }
    }
}