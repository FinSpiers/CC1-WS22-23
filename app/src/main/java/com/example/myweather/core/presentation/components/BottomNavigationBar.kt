package com.example.myweather.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myweather.R
import com.example.myweather.core.presentation.util.Screen

@Composable
fun BottomNavigationBar(navController: NavController, route: String) {
    val currentRoute = remember {
        mutableStateOf(route)
    }
    val currentNavItemSelection = remember {
        mutableStateListOf(true, false, false)
    }

    val items = listOf<BottomNavItem>(
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
    NavigationBar {
        items.forEach { item ->
            val isSelected = currentNavItemSelection[item.index]
            NavigationBarItem(
                icon = { Icon(painter = item.icon, contentDescription = item.title, modifier = Modifier.size(32.dp)) },
                label = { Text(text = item.title, style = MaterialTheme.typography.bodyMedium) },
                selected = isSelected,
                onClick = {
                    if (item.route != currentRoute.value) {
                        // Update selection state list
                        for (i in 0..2) {
                            currentNavItemSelection[i] = (item.index == i)
                        }

                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true

                            restoreState = true
                        }
                        currentRoute.value = item.route
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