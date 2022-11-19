package com.example.myweather.feature_weather.presentation.weather.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.R
import com.example.myweather.ui.theme.MyWeatherTheme

@Composable
fun LocationBar(
    locationName: String,
    dateTime: String,
    modifier: Modifier = Modifier,

    ) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = RectangleShape,
            border = BorderStroke(3.dp, MaterialTheme.colorScheme.primaryContainer),
            modifier = modifier.padding(vertical = 16.dp),
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)


                )
                Text(
                    text = locationName,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = dateTime,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationBarPreview() {
    MyWeatherTheme {
        LocationBar(locationName = "Kassel", dateTime = "19.11.2022, 09:05")
    }
}