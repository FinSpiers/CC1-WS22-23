package com.example.myweather.feature_environment_data.domain.model

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

// Implements sensor methods,... for each sensor in the Sensors class
abstract class EnvironmentSensor(
    private val context: Context,
    private val sensorFeature: String,
    val sensorType: Int
) : SensorEventListener {

    private var onSensorValuesChanged: ((List<Float>) -> Unit)? = null
    private val sensorExists: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private var isListening = false

    // saves the values from the listener
    fun setOnSensorValuesChangedListener(listener: ((List<Float>) -> Unit)) {
        onSensorValuesChanged = listener
    }

    // For checking if a listener ist already registered
    fun isListening(): Boolean {
        return isListening
    }

    // Starts a listener depending on the sensor type
    fun startListening() {
        if (!sensorExists) {
            return
        }
        if (!::sensorManager.isInitialized && sensor == null) {
            sensorManager = context.getSystemService(SensorManager::class.java) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
        }
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            isListening = true
        }
    }

    // Stops the respective listener
    fun stopListening() {
        if (!sensorExists || !::sensorManager.isInitialized) {
            return
        }
        sensorManager.unregisterListener(this)
        isListening = false
    }

    // Function to get the sensor values on sensor events
    override fun onSensorChanged(event: SensorEvent?) {
        if (!sensorExists) {
            return
        }
        if (event?.sensor?.type == sensorType) {
            onSensorValuesChanged?.invoke(event.values.toList())
        }
    }

    // Necessary for the listener but has no use for this app
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}