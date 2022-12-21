@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)

package com.example.myweather.core.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myweather.core.presentation.navigationbar.NavigationBarState
import com.example.myweather.core.presentation.navigationbar.components.BottomNavigationBar
import com.example.myweather.core.presentation.navigationbar.components.NavigationSetup
import com.example.myweather.ui.theme.MyWeatherTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    lateinit var viewModel : MainViewModel
    private lateinit var navigationState : NavigationBarState
    lateinit var permissionState : PermissionState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            viewModel = hiltViewModel()
            viewModel.setNavController(navController)
            navigationState = viewModel.navState.value
            permissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)
            checkPermissions(permissionState)
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
                        val lifecycleOwner = LocalLifecycleOwner.current
                        DisposableEffect(
                            key1 = lifecycleOwner, effect = {
                                val observer = LifecycleEventObserver { _, event ->
                                    if (event == Lifecycle.Event.ON_RESUME) {
                                        permissionState.launchPermissionRequest()
                                    }
                                }
                                lifecycleOwner.lifecycle.addObserver(observer)

                                onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
                            })
                    }
                }
            }
        }
    }

    private fun checkPermissions(permissionState: PermissionState) {
        when {
            permissionState.hasPermission -> {
                Log.e("perm", "was granted")
            }

            permissionState.shouldShowRationale -> {
                Log.e("perm", "should be requested")
            }

            !permissionState.hasPermission && !permissionState.shouldShowRationale -> {
                Log.e("perm", "was permanently denied")
            }
        }

    }
}
