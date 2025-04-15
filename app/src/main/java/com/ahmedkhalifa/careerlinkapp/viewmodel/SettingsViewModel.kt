package com.ahmedkhalifa.careerlinkapp.viewmodel

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedkhalifa.careerlinkapp.repo.settings.SettingsRepo
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsRepository: SettingsRepo,
                                            @ApplicationContext private val context: Context) : ViewModel() {

    private val _darkModeEnabled = MutableStateFlow<Boolean?>(null)
    val darkModeEnabled: StateFlow<Boolean?> = _darkModeEnabled

    private val _isUserLogin = MutableStateFlow<Boolean?>(null)
    val isUserLogin: StateFlow<Boolean?> = _isUserLogin

    private val _isFirstTimeLaunch = MutableStateFlow<Boolean?>(null)
    val isFirstTimeLaunch: StateFlow<Boolean?> = _isFirstTimeLaunch
    init {
        fetchDarkMode()
    }

    private fun fetchDarkMode() {
        viewModelScope.launch {
            settingsRepository.getDarkMode().collect { savedDarkMode ->
                val systemDarkMode = isSystemInDarkMode()
                val initialValue = savedDarkMode ?: systemDarkMode
                _darkModeEnabled.value = initialValue
                AppTheme.setDarkMode(initialValue, context) // Ensure theme is applied
            }
        }
    }

    fun setDarkModeEnabled(enabled: Boolean) {
        viewModelScope.launch {
            // Update the repository with the new setting
            settingsRepository.setDarkMode(enabled)
            _darkModeEnabled.value = enabled // Ensure the state is updated
            AppTheme.setDarkMode(enabled, context) // Apply the theme based on updated setting
        }
    }

    private fun isSystemInDarkMode(): Boolean {
        return context.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
    fun setFirstTimeLaunch(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setFirstTimeLaunch(enabled)
            _isFirstTimeLaunch.value = enabled
        }
    }

    fun setUserLogin(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setUserLoggedIn(enabled)
        }
    }

    fun checkFirstTimeLaunch() {
        viewModelScope.launch {
            settingsRepository.isFirstTimeLaunch().collect { isFirstTime ->
                _isFirstTimeLaunch.value = isFirstTime
            }
        }
    }

    fun checkUserLogin() {
        viewModelScope.launch {
            settingsRepository.isUserLoggedIn().collect { isLoggedIn ->
                _isUserLogin.value = isLoggedIn
            }
        }
    }
}
