package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal


@Composable
fun Header(title: String, subTitle: String) {
    val primaryTextColor= getColor(AppColors.AppColorSet.AppMainTextColor)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start

    ) {
        Text(
            color = primaryTextColor,
            fontFamily = Tajawal,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            text = title
        )
        Text(
            color = primaryTextColor,
            fontFamily = Tajawal,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            text = subTitle

        )

    }

}

@Preview(widthDp = 360)
@Composable
fun PreviewHeader() {
    Header(title = "title", subTitle = "subTitle")
}