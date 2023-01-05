package com.example.myweather.feature_weather.presentation.permissions

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.myweather.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.*

@SuppressLint("MissingPermission", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions(
    setLocationPermissionGranted: () -> Unit,
    setLocationPermissionDenied: () -> Unit,
) {
    // Create and remember permission state
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    // Create and remember isDialogShown mutable state
    val isDialogShown = remember { mutableStateOf(false) }

    // Check permission state
    when {
        permissionState.hasPermission -> {
            // Set location permission granted in repo
            setLocationPermissionGranted()
        }
        permissionState.shouldShowRationale -> {
            // Show rationale to user and set isDialogShown value to true
            Rationale(
                onDismiss = { isDialogShown.value = false },
                onContinue = {
                    permissionState.launchPermissionRequest()
                }
            )
            isDialogShown.value = true
        }
        !permissionState.permissionRequested -> {
            // Launch a side effect that launches a permission request
            SideEffect {
                permissionState.launchPermissionRequest()
            }
        }
        // Permission permanently denied
        !permissionState.hasPermission && !permissionState.shouldShowRationale -> {
            // Set location permission denied in repo
            setLocationPermissionDenied()

            // Make and show a toast that informs the user that the permission was denied
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.permission_permanently_denied_text),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
