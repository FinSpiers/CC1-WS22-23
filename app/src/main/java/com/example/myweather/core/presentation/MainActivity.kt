package com.example.myweather.core.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myweather.core.presentation.components.BottomNavigationBar
import com.example.myweather.core.presentation.util.NavigationSetup
import com.example.myweather.core.presentation.util.Screen
import com.example.myweather.ui.theme.MyWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MyApp(navController)
        }
    }



    @Composable
    fun MyApp(navController: NavHostController) {
        MyWeatherTheme {
            Surface {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController, route = Screen.WeatherScreen.route) }
                ) {
                    NavigationSetup(navController = navController)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MyApp(rememberNavController())
    }
}
