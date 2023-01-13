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
    // Create public and private state versions of a navigation state
    private val _navState = mutableStateOf(NavigationBarState())
    val navState: State<NavigationBarState> = _navState

    // Declare var for navController
    private var navController: NavHostController? = null

    // Function to set the navController
    fun setNavController(controller: NavHostController) {
        navController = controller
    }

    // Event handler
    fun onEvent(event: NavigationBarEvent) {
        var destination: String? = null
        when (event) {
            is NavigationBarEvent.WeatherTabClick -> {
                // Set destination to weather screen route
                destination = Screen.WeatherScreen.route
            }
            is NavigationBarEvent.EnvironmentDataTabClick -> {
                // Set destination to environment data screen route
                destination = Screen.EnvironmentDataScreen.route
            }
            is NavigationBarEvent.SettingsTabClick -> {
                // Set destination to settings screen route
                destination = Screen.SettingsScreen.route
            }
        }
        if (destination != null) {
            // Update navigation state
            _navState.value = _navState.value.copy(
                currentRoute = destination
            )

            // Navigate to the destination (if it's not selected yet), clear the backstack and
            // save the state
            if (navController != null){
                navController?.navigate(_navState.value.currentRoute) {
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
    }
}