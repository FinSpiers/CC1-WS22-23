package com.example.myweather.feature_environment_data.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myweather.feature_environment_data.domain.model.*
import com.example.myweather.feature_environment_data.domain.repository.EnvironmentDataRepository
import com.example.myweather.feature_settings.domain.model.Settings
import com.example.myweather.feature_settings.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// View-model of the app
@HiltViewModel
class EnvironmentDataViewModel @Inject constructor(
    private val environmentDataRepository: EnvironmentDataRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    // Gets the environment sensors from the repository
    private var temperatureSensor: EnvironmentSensor =
        environmentDataRepository.getTemperatureSensor()
    private var lightSensor: EnvironmentSensor =
        environmentDataRepository.getLightSensor()
    private var airPressureSensor: EnvironmentSensor =
        environmentDataRepository.getAirPressureSensor()
    private var relativeHumiditySensor: EnvironmentSensor =
        environmentDataRepository.getRelativeHumiditySensor()
    // States for the environment sensor values
    val temperatureSensorState = mutableStateOf(0.0)
    val lightSensorState = mutableStateOf(0.0)
    val airPressureSensorState = mutableStateOf(0.0)
    val relativeHumiditySensorState = mutableStateOf(0.0)

    var settings: Settings

    init {
        // Imports user settings
        runBlocking {
            settings = settingsRepository.getSettingsFromDatabase() ?: Settings()
        }

        // First sets where to save the environment sensor values from the listener
        // Then starts the listener for each environment sensor
        temperatureSensor.setOnSensorValuesChangedListener { values ->
            temperatureSensorState.value = values[0].toDouble()
        }
        if (!temperatureSensor.isListening()) {
            temperatureSensor.startListening()
        }
        lightSensor.setOnSensorValuesChangedListener { values ->
            lightSensorState.value = values[0].toDouble()
        }
        if (!lightSensor.isListening()) {
            lightSensor.startListening()
        }
        airPressureSensor.setOnSensorValuesChangedListener { values ->
            airPressureSensorState.value = values[0].toDouble()
        }
        if (!airPressureSensor.isListening()) {
            airPressureSensor.startListening()
        }
        relativeHumiditySensor.setOnSensorValuesChangedListener { values ->
            relativeHumiditySensorState.value = values[0].toDouble()
        }
        if (!relativeHumiditySensor.isListening()) {
            relativeHumiditySensor.startListening()
        }
    }

    // Stops the environment sensor listeners
    override fun onCleared() {
        super.onCleared()
        temperatureSensor.stopListening()
        airPressureSensor.stopListening()
        lightSensor.stopListening()
        relativeHumiditySensor.stopListening()
    }
}