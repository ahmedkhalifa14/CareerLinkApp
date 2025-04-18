package com.ahmedkhalifa.careerlinkapp.ui.theme

import androidx.compose.ui.graphics.Color

data class AppColors(val light: Color, val dark: Color) {

    object AppColorSet {
        val AppScreenBackgroundColor = AppColors(Color.White, Color.Black)
        val AppCardBackgroundColor =
            AppColors(AppCardLightBackgroundColor, AppCardDarkBackgroundColor)
        val AppMainTextColor= AppColors(Color.Black, Color.White)
        val AppIconColor= AppColors(Color.Black, Color.White)
        val AppSecondTextColor= AppColors(Color.Gray, Color.LightGray)
        val AppSavedColor= AppColors(Color.Red, Color.Red)
    }
}