package com.example.myweather.feature_weather.data.repository

import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.myweather.core.presentation.MainActivity
import com.example.myweather.feature_weather.data.data_source.database.CurrentWeatherDataDao
import com.example.myweather.feature_weather.data.data_source.network.interceptor.ConnectivityInterceptorImpl
import com.example.myweather.feature_weather.data.data_source.network.OpenWeatherApiService
import com.example.myweather.feature_weather.data.data_source.network.response.CurrentWeatherResponse
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Deferred

class WeatherRepositoryImpl @OptIn(ExperimentalMaterial3Api::class) constructor(
     private val dao: CurrentWeatherDataDao,
     private val apiService: OpenWeatherApiService = OpenWeatherApiService(
         ConnectivityInterceptorImpl(MainActivity().applicationContext
     )
     )
 ) : WeatherRepository {
     override suspend fun getCurrentWeatherDataFromDb(): CurrentWeatherData? {
         return dao.getCurrentWeatherData()
     }

     override suspend fun setCurrentWeatherDataInDb(weatherData: CurrentWeatherData) {
         return dao.setCurrentWeatherData(weatherData)
     }

     override suspend fun getCurrentWeatherAsync(lat : Double, lon : Double, unit : String, language : String): Deferred<CurrentWeatherResponse> {
         return apiService.getCurrentWeatherAsync(lat, lon, unit, language)
     }


 }