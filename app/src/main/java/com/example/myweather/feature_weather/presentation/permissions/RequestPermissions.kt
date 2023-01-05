package com.example.myweather.feature_weather.presentation.permissions

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import com.example.myweather.R
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.*

@SuppressLint("MissingPermission", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions(
    setLocationPermissionGranted : () -> Unit,
    setLocationPermissionDenied : () -> Unit,
) {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val isDialogShown = remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    when {
        permissionState.hasPermission -> {
            setLocationPermissionGranted()
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
            setLocationPermissionDenied()
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.permission_permanently_denied_text),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

