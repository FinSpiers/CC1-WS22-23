package com.example.myweather.feature_settings.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.feature_settings.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository : SettingsRepository
) : ViewModel() {
    // Create private and public version of settings state
    private val _state = mutableStateOf(SettingsState())
    val state : MutableState<SettingsState> = _state

    init {
        viewModelScope.launch {
            // Load settings from the database
            val loadedSettings = settingsRepository.getSettingsFromDatabase()

            // If not null update the state
            if (loadedSettings != null) {
                _state.value = _state.value.copy(isCelsius = loadedSettings.isCelsius)
            }
        }
    }

    // Function to set the isCelsius boolean of the state and save the current settings to the database
    fun setIsCelsius(bool : Boolean) {
        _state.value = _state.value.copy(isCelsius = bool, settings = _state.value.settings.apply { isCelsius = bool })
        viewModelScope.launch {
            settingsRepository.saveSettingsToDatabase(_state.value.settings)
        }
    }
}