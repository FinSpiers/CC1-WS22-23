package com.example.myweather.feature_weather.presentation

import android.annotation.SuppressLint
import android.location.LocationListener
import android.location.LocationManager
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class WeatherViewModel
@Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository,
    private val locationManager: LocationManager
) : ViewModel() {
    // Create mutable weather state
    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state

    // Create mutable state flow for isLoading
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Create vars settings, locationListener and lastKnownPosition
    private var settings: Settings
    private var locationListener: LocationListener? = null
    private var lastKnownPosition: Position? = null

    init {
        // Load the settings from the database (ensure that settings are initialised)
        runBlocking {
            settings = settingsRepository.getSettingsFromDatabase() ?: Settings()
            //lastKnownPosition = weatherRepository.getLastKnownPosition()
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                lastKnownPosition = Position(it.latitude, it.longitude)
            }
        }
        // Call initiate function to wait for permission request to finish
        initiate()

        // Launch a coroutine that loads the weather data from the database
        viewModelScope.launch {
            // Set state.isCelsius corresponding to the settings
            _state.value = _state.value.copy(isCelsius = settings.isCelsius)

            // Get weather data from database
            val weatherData = getWeatherDataFromDatabase()

            // Update state (if needed)
            if (weatherData != null) {
                _state.value = _state.value.copy(
                    timeStamp = weatherData.timeStamp,
                    weatherData = weatherData,
                    isCelsius = weatherData.isCelsius
                )
            }
            // Set state.weatherData to default weather data
            else {
                _state.value = _state.value.copy(
                    timeStamp = 0,
                    weatherData = CurrentWeatherData(),
                    isCelsius = settings.isCelsius
                )
            }
        }
        setWeatherDataFromServer()
    }

    // Function that launches a coroutine and set the location permission to granted in the repo
    fun setLocationPermissionGranted() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.setLocationPermissionGranted(true)
        }
    }

    // Function that launches a coroutine and set the location permission to denied in the repo
    fun setLocationPermissionDenied() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.setLocationPermissionDenied(true)
        }
    }

    // Recursive function that runs until either the location permission is granted or denied
    private fun initiate() {
        if (weatherRepository.locationPermissionGranted) {
            // Init location listener (ensure that listener is initialised)
            runBlocking {
                initLocationListener()
            }
            // Register location listener
            registerListener()
        } else if (!weatherRepository.locationPermissionDenied || !weatherRepository.locationPermissionGranted) {
            // launch a coroutine that delays for 1 second and then call this function again
            viewModelScope.launch {
                delay(1000)
                initiate()
            }
        }
    }

    private fun initLocationListener() {
        // Init location listener that sets the last known position in repo and fires an onLocationChange event on change
        locationListener = LocationListener { location ->
            val pos = Position(location.latitude, location.longitude)
            if (lastKnownPosition != pos) {
                lastKnownPosition = pos
                this.onEvent(WeatherEvent.OnLocationChange)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun registerListener() {
        // Register the listener if gps is enabled
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationListener?.let {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    60*1000,
                    0f,
                    it
                )
            }
        }
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        location?.let {
            val pos = Position(location.latitude, location.longitude)
            lastKnownPosition = pos
            viewModelScope.launch(Dispatchers.IO) {
                weatherRepository.setLastKnownPosition(pos)
            }
        }
    }

    private fun unregisterListener() {
        // Unregister the listener
        locationListener?.let { locationManager.removeUpdates(it) }
    }

    private fun setWeatherDataFromServer(): CurrentWeatherData? {
        viewModelScope.launch(Dispatchers.IO) {
            // Set isLoading to true for swipe to refresh
            _isLoading.value = true

            // Get the last known position by the repo
            var position = Position(0.0, 0.0)
            if (lastKnownPosition != null) {
                position = lastKnownPosition as Position
            }

            // Set unit either to celsius or fahrenheit depending on the settings
            val unit: String = if (settings.isCelsius) "metric" else "imperial"

            // Set language to english or german (depending on the system language)
            val language: String = if (Locale.current.language == "de") "de" else "en"

            try {
                // Request current weather data from server with parameters as above
                weatherRepository.getCurrentWeatherAsync(position.lat, position.lon, unit, language)
                    ?.let {
                        // Update state
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
                // Fire off NoNetworkConnection event
                onEvent(WeatherEvent.NoNetworkConnection)
            }
            // Set isLoading to false
            _isLoading.value = false
        }
        return _state.value.weatherData
    }

    private suspend fun getWeatherDataFromDatabase(): CurrentWeatherData? {
        return weatherRepository.getCurrentWeatherDataFromDb()
    }

    private suspend fun saveWeatherToDatabase(currentWeatherData: CurrentWeatherData) {
        return weatherRepository.setCurrentWeatherDataInDb(currentWeatherData)
    }

    fun onEvent(event: WeatherEvent) {
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
            is WeatherEvent.UpdateCurrentWeatherData -> {
                // Update the current weather data
                setWeatherDataFromServer()
            }
            WeatherEvent.OnLocationChange -> {
                // Request weather data for the new location
                setWeatherDataFromServer()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        unregisterListener()
    }
}