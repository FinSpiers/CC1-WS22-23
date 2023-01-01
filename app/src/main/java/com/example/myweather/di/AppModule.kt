package com.example.myweather.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import androidx.room.Room
import com.example.myweather.core.data.database.MyWeatherDatabase
import com.example.myweather.feature_environment_data.data.repository.EnvironmentDataRepositoryImpl
import com.example.myweather.feature_environment_data.domain.model.*
import com.example.myweather.feature_environment_data.domain.repository.EnvironmentDataRepository
import com.example.myweather.feature_settings.data.repository.SettingsRepositoryImpl
import com.example.myweather.feature_settings.domain.repository.SettingsRepository
import com.example.myweather.feature_weather.data.data_source.network.OpenWeatherApiService
import com.example.myweather.feature_weather.data.repository.WeatherRepositoryImpl
import com.example.myweather.feature_weather.domain.repository.WeatherRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMyWeatherDatabase(app: Application): MyWeatherDatabase {
        return Room.databaseBuilder(
            app,
            MyWeatherDatabase::class.java,
            "MyWeather_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideContext(app : Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideLocationManager(app : Application) : LocationManager {
        return app.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Provides
    @Singleton
    fun provideApiService(): OpenWeatherApiService {
        return OpenWeatherApiService()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        db: MyWeatherDatabase,
        apiService: OpenWeatherApiService
    ): WeatherRepository {
        return WeatherRepositoryImpl(db.currentWeatherDao, apiService)
    }

    @Provides
    @Singleton
    fun provideEnvironmentDataRepository(app: Application): EnvironmentDataRepository {
        return EnvironmentDataRepositoryImpl(app)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(db : MyWeatherDatabase) : SettingsRepository {
        return SettingsRepositoryImpl(db.settingsDao)
    }

    @Provides
    @Singleton
    fun provideTemperatureSensor(app: Application): EnvironmentSensor {
        return TemperatureSensor(app)
    }

    @Provides
    @Singleton
    fun provideLightSensor(app: Application): EnvironmentSensor {
        return LightSensor(app)
    }


    @Provides
    @Singleton
    fun provideAirPressureSensor(app: Application): EnvironmentSensor {
        return AirPressureSensor(app)
    }


    @Provides
    @Singleton
    fun provideRelativeHumiditySensor(app: Application): EnvironmentSensor {
        return RelativeHumiditySensor(app)
    }
}