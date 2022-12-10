package com.example.myweather.core.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.myweather.core.presentation.navigationbar.NavigationBarEvent
import com.example.myweather.core.presentation.navigationbar.NavigationBarState
import com.example.myweather.core.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _navState = mutableStateOf(NavigationBarState())
    val navState: State<NavigationBarState> = _navState
    private var navController: NavHostController? = null

    fun onEvent(event: NavigationBarEvent) {
        var destination: String? = null
        when (event) {
            is NavigationBarEvent.WeatherTabClick -> {
                destination = Screen.WeatherScreen.route
                _navState.value = _navState.value.copy(
                    currentRoute = destination,
                    isWeatherScreenSelected = true,
                    isEnvironmentDataScreenSelected = false,
                    isSettingsScreenSelected = false
                )
            }
            is NavigationBarEvent.EnvironmentDataTabClick -> {
                destination = Screen.EnvironmentDataScreen.route
                _navState.value = _navState.value.copy(
                    currentRoute = destination,
                    isWeatherScreenSelected = false,
                    isEnvironmentDataScreenSelected = true,
                    isSettingsScreenSelected = false
                )
            }
            is NavigationBarEvent.SettingsTabClick -> {
                destination = Screen.SettingsScreen.route
                _navState.value = _navState.value.copy(
                    currentRoute = destination,
                    isWeatherScreenSelected = false,
                    isEnvironmentDataScreenSelected = false,
                    isSettingsScreenSelected = true
                )
            }
        }
        if (navController != null && destination != null) {
            navController?.navigate(destination) {
                navController?.graph?.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

    fun setNavController(controller: NavHostController) {
        navController = controller
    }
}