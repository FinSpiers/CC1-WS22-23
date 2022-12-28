package com.example.myweather.feature_weather.domain.use_case

import android.util.Log
import androidx.compose.ui.text.intl.Locale
import com.example.myweather.feature_weather.data.data_source.network.NoConnectivityException
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetWeatherFromApiUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    @Throws(NoConnectivityException::class)
    suspend operator fun invoke(
        lat: Double,
        lon: Double,
        unit: String = "metric",
        language: String = Locale.current.language
    ): CurrentWeatherData? {
        try {
            return repository.getCurrentWeatherAsync(lat, lon, unit, language)
        }
        catch (e : IOException) {
            Log.e("connectivity", "Error, couldn't connect to server")
            throw NoConnectivityException()
        }
    }
}
