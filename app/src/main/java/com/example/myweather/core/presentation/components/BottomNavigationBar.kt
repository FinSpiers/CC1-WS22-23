package com.example.myweather.core.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController, route: String) {
    val currentRoute = remember {
        mutableStateOf(route)
    }
    val currentNavItemSelection = remember {
        mutableStateListOf(true, false, false)
    }

    val items = listOf<BottomNavItem>(
        BottomNavItem.Weather,
        BottomNavItem.EnvironmentData,
        BottomNavItem.Settings
    )
    NavigationBar {
        items.forEach { item ->
            val isSelected = currentNavItemSelection[item.index]
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title, style = MaterialTheme.typography.titleSmall) },
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