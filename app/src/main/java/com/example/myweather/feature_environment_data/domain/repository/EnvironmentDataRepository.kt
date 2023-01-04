package com.example.myweather.feature_environment_data.domain.repository

import com.example.myweather.feature_environment_data.domain.model.EnvironmentSensor

// This repository gives the environment sensors
interface EnvironmentDataRepository {
    fun getTemperatureSensor(): EnvironmentSensor
    fun getLightSensor(): EnvironmentSensor
    fun getAirPressureSensor(): EnvironmentSensor
    fun getRelativeHumiditySensor(): EnvironmentSensor
}