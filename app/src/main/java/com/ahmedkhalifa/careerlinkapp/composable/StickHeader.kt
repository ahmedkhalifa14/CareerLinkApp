package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal

@Composable
fun StickyHeader(title: String) {
    val textColor = getColor(AppColors.AppColorSet.AppMainTextColor)
    val stickyBackgroundColor = getColor(AppColors.AppColorSet.AppScreenBackgroundColor)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(stickyBackgroundColor),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = textColor,
            fontFamily = Tajawal
        )
        Text(
            stringResource(R.string.show_all), color = textColor,
            fontSize = 14.sp, fontFamily = Tajawal, fontWeight = FontWeight.Medium
        )
    }
}