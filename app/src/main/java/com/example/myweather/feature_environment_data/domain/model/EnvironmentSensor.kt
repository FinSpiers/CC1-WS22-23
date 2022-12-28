package com.example.myweather.feature_environment_data.domain.model


abstract class EnvironmentSensor(
    protected val sensorType: Int
) {

    protected var onSensorValuesChanged: ((List<Float>) -> Unit)? = null

    abstract val sensorExists: Boolean

    abstract fun startListening()

    abstract fun stopListening()

    abstract fun isListening() : Boolean

    fun setOnSensorValuesChangedListener(listener: ((List<Float>) -> Unit)) {
        onSensorValuesChanged = listener
    }
}