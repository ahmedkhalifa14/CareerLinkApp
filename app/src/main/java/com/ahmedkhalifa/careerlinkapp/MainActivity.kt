package com.ahmedkhalifa.careerlinkapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahmedkhalifa.careerlinkapp.composable.getColor
import com.ahmedkhalifa.careerlinkapp.graphs.Graph
import com.ahmedkhalifa.careerlinkapp.graphs.RootNavigationGraph
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppTheme
import com.ahmedkhalifa.careerlinkapp.ui.theme.CareerLinkAppTheme
import com.ahmedkhalifa.careerlinkapp.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val settingsViewModel: SettingsViewModel by viewModels()
    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        applyInitialTheme()
        setContent {
            val navController = rememberNavController()
            this.navController = navController // Store navController for use in onNewIntent
            val darkModeEnabled by settingsViewModel.darkModeEnabled.collectAsState(initial = null)
            val currentRoute by navController.currentBackStackEntryAsState()
            val isSplashScreen = currentRoute?.destination?.route == Graph.SPLASH

            // Update Status Bar whenever route or dark mode changes
            LaunchedEffect(darkModeEnabled, currentRoute) {
                if (darkModeEnabled == null) {
                    val systemDarkMode = isSystemInDarkMode()
                    settingsViewModel.setDarkModeEnabled(systemDarkMode)
                    AppTheme.setDarkMode(systemDarkMode, this@MainActivity)
                    updateStatusBarColors(systemDarkMode, isSplashScreen)
                } else {
                    AppTheme.setDarkMode(darkModeEnabled!!, this@MainActivity)
                    updateStatusBarColors(darkModeEnabled!!, isSplashScreen)
                }
            }

            // Apply the dark theme (either user preference or system theme)
            val currentTheme = darkModeEnabled ?: isSystemInDarkMode()
            CareerLinkAppTheme(darkTheme = currentTheme) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(getColor(AppColors.AppColorSet.AppScreenBackgroundColor))
                        .run {
                            if (!isSplashScreen) {
                                padding(WindowInsets.systemBars.asPaddingValues())
                            } else {
                                this
                            }
                        },
                ) {
                    RootNavigationGraph(navController = navController)
                }
            }
            // Handle deep link in LaunchedEffect to ensure NavController is ready
            LaunchedEffect(intent) {
                handleDeepLink(intent)
            }
        }
        // Initial deep link handling
        handleDeepLink(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Handle deep link when activity is already running
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        intent?.data?.let { uri ->
            Log.d("MainActivity", "Deep link received: $uri")
            if (uri.toString().startsWith("careerlink://application_status")) {
                // Extract jobId and jobTitle from URI
                val jobId = uri.getQueryParameter("jobId")
                val jobTitle = uri.getQueryParameter("jobTitle")?.let {
                    URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
                }
                Log.d("MainActivity", "Extracted jobId=$jobId, jobTitle=$jobTitle")
                // Construct the route manually
                val route = "application_status?jobId=$jobId&jobTitle=${URLEncoder.encode(jobTitle ?: "", StandardCharsets.UTF_8.toString())}"
                Log.d("MainActivity", "Navigating to route: $route")
                lifecycleScope.launch {
                    // Add slight delay to ensure NavController is ready
                    delay(100)
                    try {
                        navController.navigate(route) {
                            // Clear back stack to prevent going back to SplashScreen or HomeScreen
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        // Ensure Status Bar is updated after navigation
                        updateStatusBarColors(isSystemInDarkMode(), isSplashScreen = false)
                    } catch (e: IllegalArgumentException) {
                        Log.e("MainActivity", "Navigation failed: ${e.message}")
                    }
                }
            }
        }
    }

    private fun applyInitialTheme() {
        lifecycleScope.launch {
            settingsViewModel.darkModeEnabled.collect { enabled ->
                enabled?.let {
                    AppTheme.setDarkMode(it, this@MainActivity)
                    val isSplashScreen = navController.currentDestination?.route == Graph.SPLASH
                    updateStatusBarColors(it, isSplashScreen)
                }
            }
        }
    }

    private fun updateStatusBarColors(isDarkMode: Boolean, isSplashScreen: Boolean) {
        Log.d("MainActivity", "Updating Status Bar: isSplashScreen=$isSplashScreen, isDarkMode=$isDarkMode")
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        if (isSplashScreen) {
            // Hide Status Bar for SplashScreen
            insetsController.hide(WindowInsetsCompat.Type.statusBars())
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        } else {
            // Show Status Bar for other screens
            insetsController.show(WindowInsetsCompat.Type.statusBars())
            window.statusBarColor = if (isDarkMode) {
                ContextCompat.getColor(this, R.color.black)
            } else {
                ContextCompat.getColor(this, R.color.white)
            }
            insetsController.isAppearanceLightStatusBars = !isDarkMode
        }
    }

    private fun isSystemInDarkMode(): Boolean {
        val systemMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return systemMode == Configuration.UI_MODE_NIGHT_YES
    }
}