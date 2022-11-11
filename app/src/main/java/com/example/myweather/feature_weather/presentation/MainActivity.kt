package com.example.myweather.feature_weather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myweather.ui.theme.MyWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun ShowButtonBar(modifier: Modifier = Modifier) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .weight(1f)) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Test1")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Test2")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Test3")
            }
        }
    }
    modifier.fillMaxWidth()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyWeatherTheme {
        ShowButtonBar()
    }
}