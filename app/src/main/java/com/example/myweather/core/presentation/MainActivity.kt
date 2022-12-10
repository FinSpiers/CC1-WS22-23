@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myweather.core.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavigatorState
import androidx.navigation.compose.rememberNavController
import com.example.myweather.core.presentation.navigationbar.NavigationBarState
import com.example.myweather.core.presentation.navigationbar.components.BottomNavigationBar
import com.example.myweather.core.presentation.util.NavigationSetup
import com.example.myweather.ui.theme.MyWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    lateinit var viewModel : MainViewModel
    lateinit var navigationState : NavigationBarState
    //private var savedStates : Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            viewModel = hiltViewModel()
            viewModel.setNavController(navController)
            navigationState = viewModel.navState.value
            MyWeatherTheme {
                Surface {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(viewModel = viewModel)
                        }
                    ) {
                        NavigationSetup(
                            navController = navController,
                            startDestination = navigationState.currentRoute,
                            context = applicationContext
                        )
                    }
                }
            }
        }
    }


}
