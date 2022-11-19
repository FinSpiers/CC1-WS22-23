package com.example.myweather.feature_weather.presentation.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.ui.theme.MyWeatherTheme


val hours = listOf(0..23)

@Composable
fun WeatherHourlyBar(
    weatherData : List<Double>,
    isCelsius: Boolean,
    modifier: Modifier = Modifier
) {
    assert(weatherData.size == 24)
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer, RectangleShape)
        ) {
            Text(
                text = "Hourly forecast",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                style = MaterialTheme.typography.titleLarge
            )
        }
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            for (i in 0..23) {
                item {
                    WeatherListElement(time = i, temperature = weatherData[i], isCelsius = isCelsius)
                }
            }
        }
    }
}

@Composable
fun WeatherListElement(
    time : Int,
    temperature : Double,
    isCelsius : Boolean
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.primary)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = if (time < 10) "0${time}:00" else "${time}:00",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(8.dp)) {
            Text(
                text = "$temperature" + if (isCelsius) "°C" else "°F",
                style = MaterialTheme.typography.titleMedium,
                fontStyle = FontStyle.Italic
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherHourlyBarPreview() {
    val weatherData = listOf(1.0, 2.0, -1.5, 3.5, 1.0, 2.0, -1.5, 3.5, 1.0, 2.0, -1.5, 3.5, 1.0, 2.0, -1.5, 3.5, 1.0, 2.0, -1.5, 3.5, 1.0, 2.0, -1.5, 3.5)
    MyWeatherTheme {
        WeatherHourlyBar(
            weatherData = weatherData,
            isCelsius = true)
    }
}