package com.example.myweather.feature_weather.presentation.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myweather.R

@Composable
fun CurrentWeatherBox(
    currentTemperature: Int?,
    feelsLike: Int?,
    isCelsius: Boolean?,
    painter: Painter?,
    weatherDescription: String?,
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
                text = stringResource(id = R.string.current_weather),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        val text: String =
            if (isCelsius == true) "${currentTemperature}°C" else "${currentTemperature}°F"
        val textFeelsLike: String = if (isCelsius == true) "${feelsLike}°C" else "${feelsLike}°F"

        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        painter?.let {
            Image(
                painter = it,
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
        }

        Text(
            text = weatherDescription ?: "",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = textFeelsLike,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}
