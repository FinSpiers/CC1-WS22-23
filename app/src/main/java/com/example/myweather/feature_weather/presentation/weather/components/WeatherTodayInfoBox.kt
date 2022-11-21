package com.example.myweather.feature_weather.presentation.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.R
import com.example.myweather.ui.theme.MyWeatherTheme

@Composable
fun WeatherTodayInfoBox(
    weatherData: List<Double>,
    isCelsius: Boolean,
    dailyMinTemperature: Double,
    dailyMaxTemperature: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(3.dp, MaterialTheme.colorScheme.primaryContainer, RectangleShape),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer, RectangleShape)
        ) {
            Text(
                text = "Weather today",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.primaryContainer, RectangleShape)
        ) {
            WeatherTodayInfoBar(
                minTemperature = -6.0,
                maxTemperature = 1.5,
                isCelsius = true,
                airPressure = 1015,
                humidity = 53
            )
        }
        WeatherHourlyBar(
            weatherData = weatherData,
            isCelsius = isCelsius
        )
    }
}

@Composable
fun WeatherTodayInfoBar(
    minTemperature: Double,
    maxTemperature: Double,
    isCelsius: Boolean,
    airPressure: Int,
    humidity: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth(0.33f)) {
            Image(
                painter = painterResource(id = R.drawable.icon_barometer),
                contentDescription = null,
                modifier = Modifier.size(32.dp).padding(top = 4.dp)
            )
            Text(
                text = "$airPressure hpa",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth(0.5f)) {
            Image(
                painter = painterResource(R.drawable.icon_humidity),
                contentDescription = null,
                modifier = Modifier.size(32.dp).padding(top = 4.dp)
            )
            Text(
                text = "$humidity%",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_upward_24),
                    contentDescription = null
                )
                Text(text = if (isCelsius) "${maxTemperature}°C" else "${maxTemperature}°F")
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = if (isCelsius) "${minTemperature}°C" else "${minTemperature}°F")
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_downward_24),
                    contentDescription = "daily min temperature"
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WeatherTodayInfoBoxPreview() {
    val weatherData = listOf(
        1.0,
        2.0,
        -1.5,
        3.5,
        1.0,
        2.0,
        -1.5,
        3.5,
        1.0,
        2.0,
        -1.5,
        3.5,
        1.0,
        2.0,
        -1.5,
        3.5,
        1.0,
        2.0,
        -1.5,
        3.5,
        1.0,
        2.0,
        -1.5,
        3.5
    )
    MyWeatherTheme {
        WeatherTodayInfoBox(weatherData, true, -6.0, 1.5)
    }
}