package com.example.myweather.feature_weather.data.data_source.network

import com.example.myweather.feature_settings.domain.model.Settings
import com.example.myweather.feature_weather.data.data_source.network.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale
import javax.inject.Inject

const val API_KEY = "b098130e166bdf3ac1341c71af539675"

const val LANGUAGE_ENGLISH = "en"
const val LANGUAGE_GERMAN = "de"

const val UNITS_CELSIUS = "metric"
const val UNITS_FAHRENHEIT = "imperial"

// https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=b098130e166bdf3ac1341c71af539675&units=metric&lang=de

interface OpenWeatherApiService {
    @GET("/data/2.5/weather")
    fun getCurrentWeatherAsync (
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unit: String = if(Settings().isCelsius) UNITS_CELSIUS else UNITS_FAHRENHEIT,
        @Query("lang") language: String = if (Locale.getDefault().language == "en") LANGUAGE_ENGLISH else LANGUAGE_GERMAN
    ): Deferred<CurrentWeatherResponse>

    companion object {
        operator fun invoke(): OpenWeatherApiService {
            // build request interceptor to pass API-key in every api call
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            // build http-client that uses the request interceptor
            val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(requestInterceptor)
                .build()

            // return an implementation of OpenWeatherApiService by using retrofit builder
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApiService::class.java)
        }
    }

}