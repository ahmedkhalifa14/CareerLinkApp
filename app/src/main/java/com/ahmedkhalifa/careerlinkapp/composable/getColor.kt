package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.LocalDarkMode

@Composable
fun getColor(colorSet: AppColors): Color {
   // val isDarkTheme = isSystemInDarkTheme()

    val isDarkTheme = LocalDarkMode.current

    return if (isDarkTheme) colorSet.dark else colorSet.light
}
