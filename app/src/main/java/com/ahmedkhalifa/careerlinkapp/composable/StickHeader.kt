package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors

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
        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = textColor)
        Text("Show All", color = textColor)
    }
}