@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myweather.core.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.myweather.core.presentation.navigationbar.components.BottomNavigationBar
import com.example.myweather.core.presentation.navigationbar.components.NavigationSetup
import com.example.myweather.ui.theme.MyWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    // Declare var for viewModel
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Init and assign viewModel
            viewModel = hiltViewModel()

            // Create and remember navController
            val navController = rememberNavController()

            // Set navController in viewModel
            viewModel.setNavController(navController)

            // Get navigation state from viewModel
            val navigationState = viewModel.navState.value

            MyWeatherTheme {
                Surface {
                    // Create a scaffold with a bottom navigation bar
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                navBarState = viewModel.navState.value,
                                onEvent = viewModel::onEvent
                            )
                        }
                    ) {
                        // Setup navigation
                        NavigationSetup(
                            navController = navController,
                            startDestination = navigationState.currentRoute
                        )
                    }
                }
            }
        }
    }
}
