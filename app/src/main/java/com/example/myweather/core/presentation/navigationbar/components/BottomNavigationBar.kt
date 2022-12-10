package com.example.myweather.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myweather.R
import com.example.myweather.core.presentation.util.Screen

@Composable
fun BottomNavigationBar(navController: NavController, route : String) {
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute : String? = null
    currentRoute = if (navBackStackEntry != null && navBackStackEntry!!.destination.route != null) {
        navBackStackEntry?.destination?.route
    }
    else {
        route
    }
    NavigationBar {
        items.forEach { item ->
            val isSelected = remember { mutableStateOf(currentRoute == item.route) }
            NavigationBarItem(
                icon = { Icon(painter = item.icon, contentDescription = item.title, modifier = Modifier.size(32.dp)) },
                label = { Text(text = item.title, style = MaterialTheme.typography.bodyMedium) },
                selected = isSelected.value,
                onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
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