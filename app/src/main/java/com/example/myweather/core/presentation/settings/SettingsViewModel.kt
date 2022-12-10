package com.example.myweather.core.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.core.domain.model.Settings
import com.example.myweather.core.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository : SettingsRepository
) : ViewModel() {
    private val _state = mutableStateOf(SettingsState())
    val state : State<SettingsState> = _state

    init {
        viewModelScope.launch {
            val loadedSettings = settingsRepository.getSettingsFromDatabase()
            if (loadedSettings != null) {
                _state.value = _state.value.copy(isCelsius = loadedSettings.isCelsius)
            }
        }
    }


    fun setIsCelsius(bool : Boolean) {
        _state.value = _state.value.copy(isCelsius = bool)
        viewModelScope.launch {
            settingsRepository.saveSettingsToDatabase(Settings(isCelsius = bool))
        }
    }
}