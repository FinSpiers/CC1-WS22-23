package com.example.myweather.core.presentation.permissions

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import com.example.myweather.core.presentation.MainActivity
import com.example.myweather.feature_weather.domain.model.Position
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import com.example.myweather.feature_weather.domain.use_case.WeatherUseCases
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import kotlinx.coroutines.*
import javax.inject.Inject

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions(
    permissionState: PermissionState,
    locationManager: LocationManager,
    weatherUseCases: WeatherUseCases,
    context: Context
) {
    lateinit var locationListener: LocationListener
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner, effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    permissionState.launchPermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        })
    when {
        permissionState.hasPermission -> {
            Log.i("permission", "location permission was granted")
            locationListener = LocationListener { location ->
                val position = Position(location.latitude, location.longitude)
                Log.e("Position", position.toString())
                val job = lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    weatherUseCases.setLastKnownPositionUseCase(position)
                }
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )
        }

        permissionState.shouldShowRationale -> {
            Toast.makeText(context, "Permission should be requested", Toast.LENGTH_LONG).show()
        }

        !permissionState.hasPermission && !permissionState.shouldShowRationale -> {
            Toast.makeText(
                context,
                "Permission permanently denied. You can enable it via app settings.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

private fun checkPermissions() {

}
