package com.example.myweather.feature_weather.presentation.weather.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.R
import com.example.myweather.ui.theme.MyWeatherTheme

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
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(8.dp)) {
            Text(
                text = "$temperature" + if (isCelsius) "°C" else "°F",
                style = MaterialTheme.typography.bodyLarge,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WeatherListElementPreview(){
    MyWeatherTheme {
        WeatherListElement(
            time = 6,
            temperature = -2.5,
            isCelsius = true
            //painter = painterResource(id = R.drawable.icon_weather_sunny_24)
        )
    }
}