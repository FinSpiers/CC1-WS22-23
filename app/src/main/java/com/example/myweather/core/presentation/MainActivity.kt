@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)

package com.example.myweather.core.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myweather.core.presentation.navigationbar.NavigationBarState
import com.example.myweather.core.presentation.navigationbar.components.BottomNavigationBar
import com.example.myweather.core.presentation.navigationbar.components.NavigationSetup
import com.example.myweather.core.presentation.permissions.RequestPermissions
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import com.example.myweather.feature_weather.domain.use_case.WeatherUseCases
import com.example.myweather.ui.theme.MyWeatherTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    lateinit var viewModel: MainViewModel
    private lateinit var navigationState: NavigationBarState
    lateinit var permissionState: PermissionState
    private lateinit var locationManager: LocationManager

    @Inject
    lateinit var weatherUseCases: WeatherUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            viewModel = hiltViewModel()
            viewModel.setNavController(navController)
            navigationState = viewModel.navState.value
            permissionState =
                rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            RequestPermissions(
                permissionState = permissionState,
                locationManager = locationManager,
                weatherUseCases = weatherUseCases,
                context = this
            )

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
