package com.example.myweather.feature_environment_data.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myweather.R
import com.example.myweather.feature_environment_data.presentation.components.EnvironmentSensorDisplay
import kotlin.math.roundToInt

@Composable
fun EnvironmentDataScreen(
    viewModel: EnvironmentDataViewModel = hiltViewModel()
) {

    val temperatureSensorState = viewModel.temperatureSensorState.value.roundToInt()
    val lightSensorState = viewModel.lightSensorState.value.roundToInt()
    val airPressureSensorState = viewModel.airPressureSensorState.value.roundToInt()
    val relativeHumiditySensorState = viewModel.relativeHumiditySensorState.value.roundToInt()
    val scrollState = rememberScrollState(0)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        EnvironmentSensorDisplay(
            sensor = stringResource(id = R.string.ambient_air_temperature),
            sensorData = temperatureSensorState.toString(),
            unit = stringResource(id = R.string.celsius_unit),
            painter = painterResource(
                id = R.drawable.icon_temperature
            )
        )
        EnvironmentSensorDisplay(
            sensor = stringResource(id = R.string.illuminance),
            sensorData = lightSensorState.toString(),
            unit = stringResource(id = R.string.illuminance_unit),
            painter = painterResource(
                id = R.drawable.icon_illuminance
            )
        )
        EnvironmentSensorDisplay(
            sensor = stringResource(id = R.string.ambient_air_pressure),
            sensorData = airPressureSensorState.toString(),
            unit = stringResource(id = R.string.air_pressure_unit),
            painter = painterResource(
                id = R.drawable.icon_barometer
            )
        )
        EnvironmentSensorDisplay(
            sensor = stringResource(id = R.string.ambient_relative_humidity),
            sensorData = relativeHumiditySensorState.toString(),
            unit = stringResource(id = R.string.relative_humidity_unit),
            painter = painterResource(
                id = R.drawable.icon_humidity
            )
        )
        Spacer(modifier = Modifier.height(100.dp))

    }
}
