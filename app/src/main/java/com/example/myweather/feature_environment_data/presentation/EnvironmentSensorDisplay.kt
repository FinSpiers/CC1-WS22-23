package com.example.myweather.feature_environment_data.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.R
import com.example.myweather.ui.theme.MyWeatherTheme

@Composable
fun EnvironmentSensorDisplay(sensor: String, sensorData: Double, unit: String, painter: Painter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(2.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(0.10f)
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .padding(top = 4.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(5.dp)
        ) {
            Text(
                text = "$sensor:",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                textAlign = TextAlign.Left
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "$sensorData $unit",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp),
                textAlign = TextAlign.Right
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun EnvironmentSensorDisplayPreview() {
    MyWeatherTheme {
        EnvironmentSensorDisplay(
            sensor = "Ambient air pressure",
            sensorData = 1013.0,
            unit = "hpa",
            painter = painterResource(
                id = R.drawable.icon_barometer
            )
        )

    }
}
