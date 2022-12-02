package com.example.myweather.feature_weather.presentation.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.R

@Composable
fun CurrentWindInfobar(
    windSpeed: Double,
    windDirection: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.5f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Wind",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(4.dp)
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.icon_windspeed),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "$windSpeed m/s",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(4.dp)
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.icon_wind_direction),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = windDirection,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrentWindInfoBarPreview() {
    CurrentWindInfobar(windSpeed = 1.54, windDirection = "SE")
}