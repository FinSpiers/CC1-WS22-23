package com.example.myweather.feature_environmental_data.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.feature_environment_data.presentation.components.EnvironmentSensorDisplay
import com.example.myweather.ui.theme.MyWeatherTheme

@Composable
fun EnvironmentDataScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        EnvironmentSensorDisplay(sensor = "Ambient air temperature", sensorData = 190.0, unit = "°C")
        EnvironmentSensorDisplay(sensor = "Illuminace", sensorData = 20000.0, unit = "lx")
        EnvironmentSensorDisplay(sensor = "Ambient air pressure", sensorData = 1013.0, unit = "hpa")
        EnvironmentSensorDisplay(sensor = "Ambient relative humidity", sensorData = 35.0, unit = "%")
        Spacer(modifier = Modifier.height(16.dp))//EnvironmentSensorDisplay(sensor = "Device temperature", sensorData = 35.0, unit = "°C")
    }
}


@Preview(showBackground = true)
@Composable
fun EnvironmentDataScreenPreview() {
    MyWeatherTheme {
        EnvironmentDataScreen()

    }
}

