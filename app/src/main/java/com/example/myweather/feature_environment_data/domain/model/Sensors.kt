package com.example.myweather.feature_environment_data.domain.model

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

// This subclass inherits from the EnvironmentSensor class and
// implements the different environment sensors
class TemperatureSensor(
    context: Context
) : EnvironmentSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE,
    sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE
)

class LightSensor(
    context: Context
) : EnvironmentSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_LIGHT,
    sensorType = Sensor.TYPE_LIGHT
)

class AirPressureSensor(
    context: Context
) : EnvironmentSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_BAROMETER,
    sensorType = Sensor.TYPE_PRESSURE
)

class RelativeHumiditySensor(
    context: Context
) : EnvironmentSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY,
    sensorType = Sensor.TYPE_RELATIVE_HUMIDITY
)

