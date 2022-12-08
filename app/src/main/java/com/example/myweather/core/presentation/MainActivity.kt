@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myweather.core.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myweather.core.presentation.components.BottomNavigationBar
import com.example.myweather.core.presentation.util.NavigationSetup
import com.example.myweather.core.presentation.util.Screen
import com.example.myweather.ui.theme.MyWeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        val currentRoute = mutableStateOf(Screen.WeatherScreen.route)

        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentRoute.value = savedInstanceState.getString("currentRoute").toString()
        }
        setContent {
            navController = rememberNavController()
            MyApp(navController, currentRoute.value, applicationContext)
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp(navController: NavHostController, currentRoute: String, context: Context) {
    MyWeatherTheme {
        Surface {
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        navController = navController,
                        route = currentRoute
                    )
                }
            ) {
                NavigationSetup(navController = navController, startDestination = currentRoute, context = context)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyWeatherTheme {
        MyApp(rememberNavController(), Screen.WeatherScreen.route, MainActivity().applicationContext)
    }
}
