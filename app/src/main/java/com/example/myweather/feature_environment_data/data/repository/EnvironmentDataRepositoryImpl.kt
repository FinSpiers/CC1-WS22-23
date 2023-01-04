package com.example.myweather.feature_environment_data.data.repository


import android.content.Context
import com.example.myweather.feature_environment_data.domain.model.*
import com.example.myweather.feature_environment_data.domain.repository.EnvironmentDataRepository

// This repository gives the environment sensors
class EnvironmentDataRepositoryImpl(val context: Context) : EnvironmentDataRepository {

    override fun getTemperatureSensor(): EnvironmentSensor {
        return TemperatureSensor(context = context)
    }

    override fun getLightSensor(): EnvironmentSensor {
        return LightSensor(context)
    }

    override fun getAirPressureSensor(): EnvironmentSensor {
        return AirPressureSensor(context = context)
    }

    override fun getRelativeHumiditySensor(): EnvironmentSensor {
        return RelativeHumiditySensor(context = context)
    }

}