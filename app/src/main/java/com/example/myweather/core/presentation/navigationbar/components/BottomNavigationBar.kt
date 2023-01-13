package com.example.myweather.core.presentation.navigationbar.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myweather.R
import com.example.myweather.core.domain.model.BottomNavItem
import com.example.myweather.core.presentation.navigationbar.NavigationBarEvent
import com.example.myweather.core.presentation.navigationbar.NavigationBarState
import com.example.myweather.core.presentation.util.Screen
import kotlin.reflect.KFunction1

@Composable
fun BottomNavigationBar(navBarState: NavigationBarState, onEvent: KFunction1<NavigationBarEvent, Unit>) {
    // Create a list containing a BottomNavItem for each destination
    val items = listOf(
        BottomNavItem.Companion.Weather(
            index = 0,
            route = Screen.WeatherScreen.route,
            stringResource(id = R.string.navigation_title_weather),
            painterResource(id = R.drawable.icon_weather_32)
        ),
        BottomNavItem.Companion.EnvironmentData(
            index = 1,
            route = Screen.EnvironmentDataScreen.route,
            stringResource(id = R.string.navigation_title_environment_data),
            painterResource(id = R.drawable.icon_environment_data_32)
        ),
        BottomNavItem.Companion.Settings(
            index = 2,
            route = Screen.SettingsScreen.route,
            stringResource(id = R.string.navigation_title_settings),
            painterResource(id = R.drawable.ic_baseline_settings_24)
        ),
    )
    // Create a navigation bar
    NavigationBar {
        // Add a NavigationBarItem for each BottomNavItem
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(32.dp)
                    )
                },
                label = { Text(text = item.title, style = MaterialTheme.typography.bodyMedium) },
                // Set item selected if it's route is equal to the current route of the navBarState
                selected = item.route == navBarState.currentRoute,
                // Fire NavigationBarEvent when clicked at one of the navigationBarItem
                onClick = {
                    when (item.index) {
                        0 -> onEvent(NavigationBarEvent.WeatherTabClick)
                        1 -> onEvent(NavigationBarEvent.EnvironmentDataTabClick)
                        2 -> onEvent(NavigationBarEvent.SettingsTabClick)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}