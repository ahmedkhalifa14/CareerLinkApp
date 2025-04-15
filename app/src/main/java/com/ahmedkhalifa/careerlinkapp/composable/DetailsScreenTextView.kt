package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal

@Composable

fun DetailsScreenTextView(
    text: String,
    fontWeight: FontWeight,
    fontSize: TextUnit = 16.sp,
    modifier: Modifier = Modifier,
) {
    val textColor = getColor(AppColors.AppColorSet.AppMainTextColor)
    Text(
        text = text,
        color = textColor,
        fontSize = fontSize,
        fontWeight = fontWeight, fontFamily = Tajawal
    )
}