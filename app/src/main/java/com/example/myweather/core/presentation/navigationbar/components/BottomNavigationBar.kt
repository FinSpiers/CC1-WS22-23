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
import com.example.myweather.core.presentation.MainViewModel
import com.example.myweather.core.presentation.navigationbar.NavigationBarEvent
import com.example.myweather.core.presentation.util.Screen

@Composable
fun BottomNavigationBar(viewModel: MainViewModel) {
    val navBarState = viewModel.navState.value
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
    NavigationBar {
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
                selected = item.route == navBarState.currentRoute,
                onClick = {
                    when (item.index) {
                        0 -> viewModel.onEvent(NavigationBarEvent.WeatherTabClick)
                        1 -> viewModel.onEvent(NavigationBarEvent.EnvironmentDataTabClick)
                        2 -> viewModel.onEvent(NavigationBarEvent.SettingsTabClick)
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