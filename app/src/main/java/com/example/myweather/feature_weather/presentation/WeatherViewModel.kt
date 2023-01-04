package com.example.myweather.feature_weather.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.feature_settings.domain.model.Settings
import com.example.myweather.feature_settings.domain.repository.SettingsRepository
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.model.Position
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository,
    private val locationManager: LocationManager
) : ViewModel() {
    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state

    private var settings: Settings
    private var locationListener: LocationListener? = null
    private var lastKnownPosition: Position? = null

    init {
        runBlocking {
            settings = settingsRepository.getSettingsFromDatabase() ?: Settings()
        }
        initiate()
        viewModelScope.launch {
            _state.value = _state.value.copy(isCelsius = settings.isCelsius)
            val weatherData = getWeatherDataFromDatabase()
            if (weatherData != null) {
                _state.value = _state.value.copy(
                    timeStamp = weatherData.timeStamp,
                    weatherData = weatherData,
                    isCelsius = weatherData.isCelsius
                )
            }
            else {
                _state.value = _state.value.copy(
                    timeStamp = 0,
                    weatherData = CurrentWeatherData(),
                    isCelsius = true
                )
            }
        }
        this.onEvent(WeatherEvent.OnLocationChange)
    }

    fun setLocationPermissionGranted(){
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.setLocationPermissionGranted(true)
        }
    }
    fun setLocationPermissionDenied(){
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.setLocationPermissionDenied(true)
        }
    }


    private fun initiate() {
        if (weatherRepository.locationPermissionGranted) {
            runBlocking {
                initLocationListener()
            }
            registerListener()

        } else if (weatherRepository.locationPermissionDenied) {
            /* TODO */
        } else {
            val job = viewModelScope.launch {
                delay(1000)
                initiate()
            }
        }
    }

    private fun initLocationListener() {
        locationListener = LocationListener { location ->
            val pos = Position(location.latitude, location.longitude)
            if (lastKnownPosition != pos) {
                lastKnownPosition = pos
                Log.e("POSITION", "${pos.lat}, ${pos.lon}")
                this.onEvent(WeatherEvent.OnLocationChange)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun registerListener() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationListener?.let {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0f,
                    it
                )
            }
        }
    }

    private fun unregisterListener() {
        locationListener?.let { locationManager.removeUpdates(it) }
    }

    private fun getWeatherDataFromServer(): CurrentWeatherData? {
        viewModelScope.launch(Dispatchers.IO) {
            var position = Position(0.0, 0.0)
            if (lastKnownPosition != null) {
                position = lastKnownPosition as Position
            }

            val unit: String = if (settings.isCelsius) "metric" else "imperial"
            val language: String = if (Locale.current.language == "de") "de" else "en"
            try {
                // Request current weather data from server with parameters as above
                weatherRepository.getCurrentWeatherAsync(position.lat, position.lon, unit, language)
                    ?.let {
                        it.isCelsius = settings.isCelsius

                        _state.value = _state.value.copy(
                            timeStamp = it.timeStamp,
                            weatherData = it,
                            isCelsius = settings.isCelsius
                        )
                        // Save current weather data to database
                        saveWeatherToDatabase(it)
                    }
            } catch (e: IOException) {
                onEvent(WeatherEvent.NoNetworkConnection)
            }
        }
        return _state.value.weatherData
    }

    private suspend fun getWeatherDataFromDatabase(): CurrentWeatherData? {
        return weatherRepository.getCurrentWeatherDataFromDb()
    }

    private suspend fun saveWeatherToDatabase(currentWeatherData: CurrentWeatherData) {
        return weatherRepository.setCurrentWeatherDataInDb(currentWeatherData)
    }

    private fun onEvent(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.NoNetworkConnection -> {
                viewModelScope.launch {
                    // Load weather data from database
                    val weatherData = getWeatherDataFromDatabase()
                    if (weatherData != null) {
                        // If unit Celsius is selected in settings but data was stored in Fahrenheit
                        if (settings.isCelsius && !_state.value.isCelsius) {
                            // Convert weather data to Celsius
                            _state.value = _state.value.copy(
                                timeStamp = weatherData.timeStamp,
                                weatherData = weatherData.convertToCelsius(),
                                isCelsius = true
                            )
                        }
                        // If unit Fahrenheit is selected in settings but data was stored in Celsius
                        else if (!settings.isCelsius && _state.value.isCelsius) {
                            // Convert weather data to Fahrenheit
                            _state.value = _state.value.copy(
                                timeStamp = weatherData.timeStamp,
                                weatherData = weatherData.convertToFahrenheit(),
                                isCelsius = false
                            )
                        } else {
                            _state.value = _state.value.copy(
                                timeStamp = weatherData.timeStamp,
                                weatherData = weatherData,
                                isCelsius = weatherData.isCelsius
                            )
                        }
                    }
                    // If weather data couldn't be loaded
                    else {
                        _state.value = _state.value.copy(
                            weatherData = CurrentWeatherData(),
                            isCelsius = true
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
            WeatherEvent.OnLocationChange -> {
                viewModelScope.launch {
                    if (lastKnownPosition != null) {
                        val weatherData = getWeatherDataFromServer()
                        if (weatherData != null) {
                            _state.value = _state.value.copy(
                                timeStamp = weatherData.timeStamp,
                                weatherData = weatherData,
                                isCelsius = weatherData.isCelsius
                            )
                            saveWeatherToDatabase(weatherData)
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        unregisterListener()
    }
}