package com.ahmedkhalifa.careerlinkapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedkhalifa.careerlinkapp.repo.settings.SettingsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsRepository: SettingsRepo) :
    ViewModel() {
    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled
    private val _isUserLogin = MutableStateFlow(false)
    val isUserLogin: StateFlow<Boolean> = _isUserLogin
    private val _isFirstTimeLaunch = MutableStateFlow(false)
    val isFirstTimeLaunch: StateFlow<Boolean> = _isFirstTimeLaunch


    fun isFirstTimeLaunch() {
        viewModelScope.launch {
            settingsRepository.isFirstTimeLaunch().collect {
                _isFirstTimeLaunch.value = it
            }
        }

    }

    fun isUserLogin() {
        viewModelScope.launch {
            settingsRepository.isUserLoggedIn().collect {
                _isFirstTimeLaunch.value = it
            }
        }
    }

    fun isDarkMode() {
        viewModelScope.launch {
            settingsRepository.getDarkMode().collect {
                _darkModeEnabled.value = it
            }
        }
    }


    fun setDarkModeEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDarkMode(enabled)
        }
    }

    fun setFirstTimeLaunch(enabled: Boolean) {
        viewModelScope.launch {
            Log.d("SettingsViewModel", "setFirstTimeLaunch called with: $enabled")
            settingsRepository.setFirstTimeLaunch(enabled)
            _isFirstTimeLaunch.value = enabled
        }
    }

    fun setUserLogin(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setUserLoggedIn(enabled)
        }
    }
}