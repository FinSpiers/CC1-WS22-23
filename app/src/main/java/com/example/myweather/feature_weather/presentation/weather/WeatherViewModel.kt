package com.example.myweather.feature_weather.presentation.weather

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.feature_weather.data.data_source.network.NoConnectivityException
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.use_case.WeatherUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCases: WeatherUseCases
) : ViewModel() {
    private val _state = mutableStateOf(WeatherState(weatherData = null))
    val state: State<WeatherState> = _state

    init {
        getWeatherData()
    }

    private fun getWeatherData() {
        val lat = 51.517122
        val lon = 9.417862

        viewModelScope.launch {
            val weatherData = getWeatherDataFromDatabase()
            if (weatherData != null) {
                _state.value = _state.value.copy(timeStamp = weatherData.timeStamp, weatherData = weatherData)
            }
            try {
                getWeatherDataFromApi(lat, lon).collectLatest { currentWeatherData ->
                    _state.value = _state.value.copy(
                        timeStamp = System.currentTimeMillis(),
                        weatherData = currentWeatherData
                    )
                }
                _state.value.weatherData?.let { saveWeatherToDatabase(it) }
            } catch (e: NoConnectivityException) {
                onEvent(WeatherEvent.NoNetworkConnection)
            }
        }
    }

    private suspend fun getWeatherDataFromApi(
        lat: Double,
        lon: Double,
        unit: String = "metric",
        language: String = "en"
    ): Flow<CurrentWeatherData?> {
        return weatherUseCases.getWeatherFromApiUseCase(lat, lon, unit, language)
    }

    private suspend fun getWeatherDataFromDatabase(): CurrentWeatherData? {
        return weatherUseCases.getWeatherFromDatabaseUseCase()
    }

    private suspend fun saveWeatherToDatabase(currentWeatherData: CurrentWeatherData) {
        weatherUseCases.saveWeatherToDatabaseUseCase(currentWeatherData)
    }


    private fun onEvent(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.NoNetworkConnection -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(weatherData = getWeatherDataFromDatabase())
                }
            }
            is WeatherEvent.RequestLocationPermission -> {
                TODO()
            }
            is WeatherEvent.UpdateCurrentWeatherData -> {
                getWeatherData()
            }
        }
    }
}