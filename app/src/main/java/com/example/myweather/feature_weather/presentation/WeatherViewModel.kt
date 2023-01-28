package com.example.myweather.feature_weather.presentation

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.R
import com.example.myweather.feature_settings.domain.model.Settings
import com.example.myweather.feature_settings.domain.repository.SettingsRepository
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.model.Position
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class WeatherViewModel
@Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository,
    private val connectivityManager: ConnectivityManager,
    private val locationManager: LocationManager,
    private val fusedLocationManager: FusedLocationProviderClient
) : ViewModel() {
    // Create mutable weather state
    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state

    // Create mutable state flow for isLoading
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Create scrollState for for the view
    val scrollState: ScrollState = ScrollState(0)

    // Create vars settings and lastKnownPosition
    private var settings: Settings
    private var lastKnownPosition: Position? = null

    init {
        // Load the settings from the database (ensure that settings are initialised)
        runBlocking {
            settings = settingsRepository.getSettingsFromDatabase() ?: Settings()
        }
        viewModelScope.launch {
            // Set state.isCelsius corresponding to the settings
            _state.value = _state.value.copy(isCelsius = settings.isCelsius)

            // Get weather data from database
            val weatherData = getWeatherDataFromDatabase()

            // Update state
            _state.value = _state.value.copy(
                timeStamp = weatherData.timeStamp,
                weatherData = weatherData,
                isCelsius = weatherData.isCelsius
            )
            waitForPermissionRequest()
        }
    }

    // Function that blocks the refresh until the permission is granted
    private fun waitForPermissionRequest() {
        if (weatherRepository.locationPermissionGranted) {
            refresh()
        } else if (!weatherRepository.locationPermissionGranted && !weatherRepository.locationPermissionDenied) {
            viewModelScope.launch {
                delay(1000)
                waitForPermissionRequest()
            }
        }
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

    private fun refresh() {
        // Set isLoading to true for swipe to refresh
        _isLoading.value = true

        // Check network state
        if (connectivityManager.activeNetwork == null) {
            onEvent(WeatherEvent.NoNetworkConnection)
        }
        // Check GPS state and location permission
        else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !weatherRepository.isLocationPermissionGranted()) {
            onEvent(WeatherEvent.NoGpsSignal)
        } else {
            // Request location update to trigger listener initially
            fusedLocationManager.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, null
            )

            // Add onSuccessListener for lastLocation from fusedLocationManager
            fusedLocationManager.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    // Set last known position
                    lastKnownPosition = Position(location.latitude, location.longitude)


                    // Request weather data from server
                    viewModelScope.launch(Dispatchers.IO) {
                        setWeatherDataFromServer()
                        _isLoading.value = false
                    }
                } else {
                    _isLoading.value = false
                }
            }
        }
    }

    private suspend fun setWeatherDataFromServer() {
        // Get the last known position by the repo
        val position = lastKnownPosition

        if (position != null) {
            // Set unit either to celsius or fahrenheit depending on the settings
            val unit: String = if (settings.isCelsius) "metric" else "imperial"

            // Set language to english or german (depending on the system language)
            val language: String = if (Locale.current.language == "de") "de" else "en"

            // Request current weather data from server with parameters as above
            weatherRepository.getCurrentWeatherAsync(
                position.lat, position.lon, unit, language
            )?.let {
                // Update state
                it.isCelsius = settings.isCelsius
                _state.value = _state.value.copy(
                    timeStamp = it.timeStamp, weatherData = it, isCelsius = settings.isCelsius
                )
                // Save current weather data to database
                saveWeatherToDatabase(it)
            }

        }
    }

    private suspend fun getWeatherDataFromDatabase(): CurrentWeatherData {
        var weatherData = weatherRepository.getCurrentWeatherDataFromDb()
        if (weatherData != null) {
            // If unit Celsius is selected in settings but data was stored in Fahrenheit
            if (settings.isCelsius && !weatherData.isCelsius) {
                // Convert weather data to Celsius
                weatherData = weatherData.convertToCelsius()
            }
            // If unit Fahrenheit is selected in settings but data was stored in Celsius
            else if (!settings.isCelsius && weatherData.isCelsius) {
                // Convert weather data to Fahrenheit
                weatherData = weatherData.convertToFahrenheit()
            }
        }
        // If weather data couldn't be loaded
        else {
            weatherData = CurrentWeatherData().apply {
                location =
                    weatherRepository.getContextInstance().getString(R.string.initial_location_text)
                currentWeatherDescription = weatherRepository.getContextInstance()
                    .getString(R.string.initial_weather_description_text)
                currentWeatherMain = "Initial"
            }
        }
        return weatherData
    }

    private suspend fun saveWeatherToDatabase(currentWeatherData: CurrentWeatherData) {
        return weatherRepository.setCurrentWeatherDataInDb(currentWeatherData)
    }

    fun onEvent(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.UpdateCurrentWeatherData -> {
                // Update the current weather data
                refresh()
            }
            is WeatherEvent.NoNetworkConnection -> {
                // Inform user that there's no internet connection
                viewModelScope.launch {
                    makeAndShowToast(
                        weatherRepository.getContextInstance()
                            .getString(R.string.no_internet_connection_text)
                    )
                }
            }
            WeatherEvent.NoGpsSignal -> {
                // Inform the user that there's no gps signal
                makeAndShowToast(
                    weatherRepository.getContextInstance().getString(R.string.gps_disabled_text)
                )
            }
        }
    }

    private fun makeAndShowToast(text: String) {
        Toast.makeText(
            weatherRepository.getContextInstance(), text, Toast.LENGTH_LONG
        ).show()
    }
}