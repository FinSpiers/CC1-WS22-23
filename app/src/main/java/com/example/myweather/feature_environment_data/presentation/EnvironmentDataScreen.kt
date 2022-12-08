package com.example.myweather.feature_environment_data.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.R
import com.example.myweather.feature_environment_data.presentation.components.EnvironmentSensorDisplay
import com.example.myweather.ui.theme.MyWeatherTheme

@Composable
fun EnvironmentDataScreen() {
    val scrollState = rememberScrollState(0)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp).verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        EnvironmentSensorDisplay(
            sensor = stringResource(id = R.string.ambient_air_temperature),
            sensorData = 190.0,
            unit = "°C",
            painter = painterResource(
                id = R.drawable.icon_temperature
            )
        )
        EnvironmentSensorDisplay(
            sensor = stringResource(id = R.string.illuminance),
            sensorData = 20000.0,
            unit = "lx",
            painter = painterResource(
                id = R.drawable.icon_illuminance
            )
        )
        EnvironmentSensorDisplay(
            sensor = stringResource(id = R.string.ambient_air_pressure),
            sensorData = 1013.0,
            unit = "hpa",
            painter = painterResource(
                id = R.drawable.icon_barometer
            )
        )
        EnvironmentSensorDisplay(
            sensor = stringResource(id = R.string.ambient_relative_humidity),
            sensorData = 35.0,
            unit = "%",
            painter = painterResource(
                id = R.drawable.icon_humidity
            )
        )
        Spacer(modifier = Modifier.height(100.dp))

    }
}


@Preview(showBackground = true)
@Composable
fun EnvironmentDataScreenPreview() {
    MyWeatherTheme {
        EnvironmentDataScreen()
    }
}

