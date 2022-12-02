package com.example.myweather.feature_weather.data.repository

import com.example.myweather.feature_weather.data.data_source.database.CurrentWeatherDataDao
import com.example.myweather.feature_weather.domain.model.CurrentWeatherData
import com.example.myweather.feature_weather.domain.repository.WeatherRepository

 class WeatherRepositoryImpl(private val dao: CurrentWeatherDataDao) : WeatherRepository {
     override suspend fun getCurrentWeatherData(): CurrentWeatherData? {
         return dao.getCurrentWeatherData()
     }

     override suspend fun setCurrentWeatherData(weatherData: CurrentWeatherData) {
         return dao.setCurrentWeatherData(weatherData)
     }

 }