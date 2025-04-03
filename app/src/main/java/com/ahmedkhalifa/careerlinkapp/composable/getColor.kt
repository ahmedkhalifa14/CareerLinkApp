package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors

@Composable
fun getColor(colorSet: AppColors): Color {
    val isDarkTheme = isSystemInDarkTheme()
    return if (isDarkTheme) colorSet.dark else colorSet.light
}
