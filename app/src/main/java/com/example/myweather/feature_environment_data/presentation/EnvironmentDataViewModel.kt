package com.example.myweather.feature_environment_data.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myweather.feature_environment_data.domain.repository.EnvironmentDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnvironmentDataViewModel @Inject constructor(
    private val environmentDataRepository: EnvironmentDataRepository,
) : ViewModel() {

    val temperatureSensorState = mutableStateOf(0.0)
    val lightSensorState = mutableStateOf(0.0)
    val airPressureSensorState = mutableStateOf(0.0)
    val relativeHumiditySensorState = mutableStateOf(0.0)

    init {
        val temperatureSensor = environmentDataRepository.getTemperatureSensor()
        val lightSensor = environmentDataRepository.getLightSensor()
        val airPressureSensor = environmentDataRepository.getAirPressureSensor()
        val relativeHumiditySensor =
            environmentDataRepository.getRelativeHumiditySensor()

        temperatureSensor.startListening()
        temperatureSensor.setOnSensorValuesChangedListener { values ->
            temperatureSensorState.value = values[0].toDouble()
        }

        lightSensor.startListening()
        lightSensor.setOnSensorValuesChangedListener { values ->
            lightSensorState.value = values[0].toDouble()
        }

        airPressureSensor.startListening()
        airPressureSensor.setOnSensorValuesChangedListener { values ->
            airPressureSensorState.value = values[0].toDouble()
        }

        relativeHumiditySensor.startListening()
        relativeHumiditySensor.setOnSensorValuesChangedListener { values ->
            relativeHumiditySensorState.value = values[0].toDouble()
        }

    }

}