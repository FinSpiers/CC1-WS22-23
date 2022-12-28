package com.example.myweather.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.myweather.feature_environment_data.data.repository.EnvironmentDataRepositoryImpl
import com.example.myweather.feature_environment_data.domain.model.*
import com.example.myweather.feature_environment_data.domain.repository.EnvironmentDataRepository
import com.example.myweather.core.data.data_source.SettingsDatabase
import com.example.myweather.core.data.repository.SettingsRepositoryImpl
import com.example.myweather.core.domain.repository.SettingsRepository
import com.example.myweather.feature_weather.data.data_source.database.WeatherDatabase
import com.example.myweather.feature_weather.data.data_source.network.OpenWeatherApiService
import com.example.myweather.feature_weather.data.repository.WeatherRepositoryImpl
import com.example.myweather.feature_weather.domain.repository.WeatherRepository
import com.example.myweather.feature_weather.domain.use_case.*

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
    fun provideSettingsDatabase(app: Application): SettingsDatabase {
        return Room.databaseBuilder(
            app,
            SettingsDatabase::class.java,
            SettingsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideContext(app : Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(db : SettingsDatabase) : SettingsRepository {
        return SettingsRepositoryImpl(db.settingsDao())
    }

    @Provides
    @Singleton
    fun provideApiService(): OpenWeatherApiService {
        return OpenWeatherApiService()
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(app: Application): WeatherDatabase {
        return Room.databaseBuilder(
            app,
            WeatherDatabase::class.java,
            WeatherDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        db: WeatherDatabase,
        apiService: OpenWeatherApiService
    ): WeatherRepository {
        return WeatherRepositoryImpl(db.weatherDataDao(), apiService)
    }

    @Provides
    @Singleton
    fun provideWeatherUseCases(repository: WeatherRepository): WeatherUseCases {
        return WeatherUseCases(
            getWeatherFromApiUseCase = GetWeatherFromApiUseCase(repository),
            getWeatherFromDatabaseUseCase = GetWeatherFromDatabaseUseCase(repository),
            saveWeatherToDatabaseUseCase = SaveWeatherToDatabaseUseCase(repository),
            getLastKnownPositionUseCase = GetLastKnownPositionUseCase(repository),
            setLastKnownPositionUseCase = SetLastKnownPositionUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideEnvironmentDataRepository(app: Application): EnvironmentDataRepository {
        return EnvironmentDataRepositoryImpl(app)
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