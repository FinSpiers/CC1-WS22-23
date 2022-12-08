package com.example.myweather.feature_weather.data.repository

import com.example.myweather.feature_weather.data.data_source.database.CurrentWeatherDataDao
import com.example.myweather.feature_weather.data.data_source.network.OpenWeatherApiService
import com.example.myweather.feature_weather.data.data_source.network.response.toCurrentWeatherData
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.repository.WeatherRepository

class WeatherRepositoryImpl (
     private val dao: CurrentWeatherDataDao,
     private val apiService: OpenWeatherApiService = OpenWeatherApiService()
 ) : WeatherRepository {
     override suspend fun getCurrentWeatherDataFromDb(): CurrentWeatherData? {
         return dao.getCurrentWeatherData()
     }

     override suspend fun setCurrentWeatherDataInDb(weatherData: CurrentWeatherData) {
         return dao.setCurrentWeatherData(weatherData)
     }

     override suspend fun getCurrentWeatherAsync(lat : Double, lon : Double, unit : String, language : String) : CurrentWeatherData {
         return apiService.getCurrentWeatherAsync(lat, lon, unit, language).await().toCurrentWeatherData()
     }


 }