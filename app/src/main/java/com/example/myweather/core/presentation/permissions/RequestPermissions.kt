package com.example.myweather.core.presentation.permissions

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.*

@SuppressLint("MissingPermission", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions(
    weatherRepository: WeatherRepository,
    context: Context
) {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val isDialogShown = remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    when {
        permissionState.hasPermission -> {
            val job = lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                weatherRepository.setLocationPermissionGranted(true)
            }
            runBlocking {
                job.join()
            }
        }
        permissionState.shouldShowRationale -> {
            Rationale(
                onDismiss = { isDialogShown.value = false },
                onContinue = { permissionState.launchPermissionRequest() })
            isDialogShown.value = true
        }

        !permissionState.permissionRequested -> {
            lifecycleOwner.lifecycleScope.launchWhenResumed {
                permissionState.launchPermissionRequest()
            }
        }
        !permissionState.hasPermission && !permissionState.shouldShowRationale -> {
            Toast.makeText(
                context,
                "Permission permanently denied. You can enable it via app settings.",
                Toast.LENGTH_LONG
            ).show()
            lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                weatherRepository.setLocationPermissionDenied(true)
            }
        }
    }
}

