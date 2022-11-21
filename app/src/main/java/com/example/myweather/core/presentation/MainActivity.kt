package com.example.myweather.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myweather.feature_environmental_data.presentation.EnvironmentDataScreen
import com.example.myweather.feature_weather.presentation.weather.WeatherScreen
import com.example.myweather.ui.theme.MyWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowWeatherScreen()
        }
    }

    @Composable
    fun ShowEnvironmentalDataScreen(){
        MyWeatherTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                EnvironmentDataScreen()
            }
        }
    }

    @Composable
    fun ShowWeatherScreen() {
        MyWeatherTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                WeatherScreen()
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ShowWeatherScreen()
    }
}