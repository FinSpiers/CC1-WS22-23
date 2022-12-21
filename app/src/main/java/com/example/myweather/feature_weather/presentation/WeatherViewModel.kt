package com.example.myweather.feature_weather.presentation

import android.Manifest
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
import com.example.myweather.feature_weather.domain.use_case.WeatherUseCases
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCases: WeatherUseCases,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _state = mutableStateOf(WeatherState())
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
                _state.value = _state.value.copy(
                    timeStamp = weatherData.timeStamp,
                    weatherData = weatherData,
                    isCelsius = weatherData.isCelsius
                )
            }
            try {
                if (settingsRepository.getSettingsFromDatabase() != null) {
                    val settings = settingsRepository.getSettingsFromDatabase() as Settings
                    _state.value = _state.value.copy(isCelsius = settings.isCelsius)
                }
                val unit = if (_state.value.isCelsius) "metric" else "imperial"
                val language = if(Locale.current.language == "de") "de" else "en"

                getWeatherDataFromApi(lat, lon, unit, language).collectLatest { currentWeatherData ->
                    _state.value = _state.value.copy(
                        timeStamp = System.currentTimeMillis(),
                        weatherData = currentWeatherData,
                        isCelsius = _state.value.isCelsius
                    )
                }
                _state.value.weatherData?.let { saveWeatherToDatabase(it) }

                //TODO delete
                Log.e("wth", state.value.weatherData?.currentWeatherMain.toString())

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
                    val weatherData = getWeatherDataFromDatabase()
                    _state.value =
                        weatherData?.let {
                            _state.value.copy(
                                weatherData = weatherData,
                                isCelsius = it.isCelsius
                            )
                        }!!
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