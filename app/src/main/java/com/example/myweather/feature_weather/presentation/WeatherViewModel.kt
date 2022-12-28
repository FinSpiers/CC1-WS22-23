package com.example.myweather.feature_weather.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.core.domain.model.Settings
import com.example.myweather.core.domain.repository.SettingsRepository
import com.example.myweather.feature_weather.data.data_source.network.NoConnectivityException
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.model.Position
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import com.example.myweather.feature_weather.domain.use_case.WeatherUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject constructor(
    private val weatherUseCases: WeatherUseCases,
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state
    private lateinit var settings: Settings

    init {
        viewModelScope.launch {
            val settingsJob = viewModelScope.launch(Dispatchers.IO) {
                var loadedSettings = settingsRepository.getSettingsFromDatabase()
                if (loadedSettings != null) {
                    settings = loadedSettings
                }
                else {
                    settings = Settings()
                }
                //settingsRepository.getSettingsFromDatabase()?.let {
                //    loadedSettings = it
                //    Log.e("Settings", it.toString())
                //}
            }
            settingsJob.join()
            _state.value = _state.value.copy(isCelsius = settings.isCelsius)

            val requestDataFromDatabaseJob = async {
                getWeatherDataFromDatabase()?.let {
                    _state.value = _state.value.copy(
                        timeStamp = it.timeStamp,
                        weatherData = it,
                        isCelsius = it.isCelsius
                    )
                }
            }
            val requestDataFromServerJob = async {
                getWeatherDataFromServer()?.let {
                    _state.value = _state.value.copy(
                        timeStamp = it.timeStamp,
                        weatherData = it,
                        isCelsius = it.isCelsius
                    )
                }
            }
            requestDataFromDatabaseJob.await()

        }
    }

    private suspend fun getWeatherDataFromServer() : CurrentWeatherData? {
        var position = Position(0.0, 0.0)
        val getLastPositionJob = viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getLastKnownPosition()?.let {
                position = it
            }
        }
        getLastPositionJob.join()
        Log.e("Position", "lat: ${position.lat}, lon: ${position.lon}")

        val unit: String = if (settings.isCelsius) "metric" else "imperial"
        val language: String = if (Locale.current.language == "de") "de" else "en"
        try {
            // Request current weather data from server with parameters as above
            val weatherData = getWeatherDataFromApi(position.lat, position.lon, unit, language)?.let {
                it.isCelsius = settings.isCelsius

                _state.value = _state.value.copy(
                    timeStamp = it.timeStamp,
                    weatherData = it,
                    isCelsius = it.isCelsius
                )
                // Save current weather data to database
                saveWeatherToDatabase(it)
            }
        } catch (e: NoConnectivityException) {
            onEvent(WeatherEvent.NoNetworkConnection)
        }
        return _state.value.weatherData
    }
    private suspend fun getWeatherDataFromApi(
        lat: Double,
        lon: Double,
        unit: String = "metric",
        language: String = "en"
    ): CurrentWeatherData? {
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
                    // Load weather data from database
                    val weatherData = getWeatherDataFromDatabase()
                    // If unit Celsius is selected in settings but data was stored in Fahrenheit
                    if (settings.isCelsius && !_state.value.isCelsius) {
                        // Convert weather data to Celsius
                        _state.value = _state.value.copy(
                            weatherData = weatherData?.convertToCelsius(),
                            isCelsius = true
                        )
                    }
                    // If unit Fahrenheit is selected in settings but data was stored in Celsius
                    else if (!settings.isCelsius && _state.value.isCelsius) {
                        // Convert weather data to Fahrenheit
                        _state.value = _state.value.copy(
                            weatherData = weatherData?.convertToFahrenheit(),
                            isCelsius = false
                        )
                    }
                    else {
                        _state.value = _state.value.copy(
                            weatherData = weatherData,
                            isCelsius = weatherData!!.isCelsius
                        )
                    }
                }
            }
            is WeatherEvent.RequestLocationPermission -> {
                TODO()
            }
            is WeatherEvent.UpdateCurrentWeatherData -> {
                viewModelScope.launch {
                    getWeatherDataFromServer()
                }
            }
        }
    }
}