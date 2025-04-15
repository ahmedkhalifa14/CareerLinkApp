package com.ahmedkhalifa.careerlinkapp.screens.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.composable.SettingsItem
import com.ahmedkhalifa.careerlinkapp.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val darkModeEnabled by viewModel.darkModeEnabled.collectAsState(initial = null)
    LaunchedEffect(darkModeEnabled) {
        darkModeEnabled.let {
            AppCompatDelegate.setDefaultNightMode(
                if (it == true) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }
    }
    if (darkModeEnabled != null) {
        SettingsScreenContent(
            darkModeEnabled = darkModeEnabled!!,
            onDarkModeChange = { enabled ->
                viewModel.setDarkModeEnabled(enabled)
            }
        )
    } else {
        LoadingView()
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun SettingsScreenContent(
    darkModeEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SettingsItem(
            title = stringResource(R.string.dark_mode),
            subtitle = stringResource(R.string.enable_or_disable_dark_mode),
            isChecked = darkModeEnabled,
            onCheckedChange = onDarkModeChange
        )
    }
}

