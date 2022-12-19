package com.example.myweather.feature_weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.R
import com.example.myweather.ui.theme.MyWeatherTheme

@Composable
fun CurrentInformationBox(
    isCelsius: Boolean,
    minTemperature: Double,
    maxTemperature: Double,
    airPressure: Int,
    humidity: Int,
    windSpeed: Double,
    windDirection: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .border(3.dp, MaterialTheme.colorScheme.primaryContainer, RectangleShape),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer, RectangleShape)
        ) {
            Text(
                text = stringResource(id = R.string.current_information),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        CurrentWindInfoBar(
            windSpeed = windSpeed,
            windDirection = windDirection
        )

        CurrentInformationBar(
            minTemperature = minTemperature,
            maxTemperature = maxTemperature,
            isCelsius = isCelsius,
            airPressure = airPressure,
            humidity = humidity
        )

    }
}

@Preview(showBackground = true)
@Composable
fun WeatherTodayInfoBoxPreview() {
    MyWeatherTheme {
        CurrentInformationBox(true, -6.0, 1.5, 1017, 51, 1.54, "NE")
    }
}