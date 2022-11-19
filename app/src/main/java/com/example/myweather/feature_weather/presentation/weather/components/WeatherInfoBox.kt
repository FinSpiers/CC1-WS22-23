package com.example.myweather.feature_weather.presentation.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.R
import com.example.myweather.ui.theme.MyWeatherTheme

@Composable
fun WeatherInfoBox(
    currentTemperature: Double,
    isCelsius: Boolean,
    painter: Painter,
    weatherDescription: String,
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
                text = "Current weather",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        Text(
            text = if (isCelsius) "${currentTemperature}°C" else "${currentTemperature}°F",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Image(
            painter = painter,
            contentDescription = "Default weather description"
        )

        Text(
            text = weatherDescription,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherInfoBoxPreview() {
    MyWeatherTheme {
        WeatherInfoBox(
            currentTemperature = -1.5,
            isCelsius = true,
            painter = painterResource(id = R.drawable.image_weather_cloudy),
            weatherDescription = "No discription available"
        )
    }
}