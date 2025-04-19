package com.ahmedkhalifa.careerlinkapp

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.ahmedkhalifa.careerlinkapp.graphs.RootNavigationGraph
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppTheme
import com.ahmedkhalifa.careerlinkapp.ui.theme.CareerLinkAppTheme
import com.ahmedkhalifa.careerlinkapp.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val settingsViewModel: SettingsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applyInitialTheme()
        setContent {
            val navController = rememberNavController()
            val darkModeEnabled by settingsViewModel.darkModeEnabled.collectAsState(initial = null)

            LaunchedEffect(darkModeEnabled) {

                if (darkModeEnabled == null) {
                    val systemDarkMode = isSystemInDarkMode()

                    settingsViewModel.setDarkModeEnabled(systemDarkMode)
                    AppTheme.setDarkMode(systemDarkMode, this@MainActivity)
                    updateStatusBarColors(systemDarkMode)
                } else {
                    AppTheme.setDarkMode(darkModeEnabled!!, this@MainActivity)
                    updateStatusBarColors(darkModeEnabled!!)
                }
            }

            // Apply the dark theme (either user preference or system theme)
            val currentTheme = darkModeEnabled ?: isSystemInDarkMode()
            CareerLinkAppTheme(darkTheme = currentTheme) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(com.ahmedkhalifa.careerlinkapp.composable.getColor(AppColors.AppColorSet.AppScreenBackgroundColor))
                        .padding(WindowInsets.statusBars.asPaddingValues()),
                ) {
                    RootNavigationGraph(navController =navController)
                }
            }
        }
    }

    private fun applyInitialTheme() {
        lifecycleScope.launch {
            settingsViewModel.darkModeEnabled.collect { enabled ->
                enabled?.let {
                    AppTheme.setDarkMode(it, this@MainActivity)
                    updateStatusBarColors(it)
                }
            }
        }
    }

    private fun updateStatusBarColors(isDarkMode: Boolean) {
        window.statusBarColor = if (isDarkMode) {
            ContextCompat.getColor(this, R.color.black)
        } else {
            ContextCompat.getColor(this, R.color.white)
        }
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
            !isDarkMode
    }

    private fun isSystemInDarkMode(): Boolean {
        val systemMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isDarkMode = systemMode == Configuration.UI_MODE_NIGHT_YES
        return isDarkMode
    }
}
