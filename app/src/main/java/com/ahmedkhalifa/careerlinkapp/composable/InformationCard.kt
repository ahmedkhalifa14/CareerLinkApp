package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal

@Composable
fun InformationCard(
    title: String,
    info: String,
    onTextChange: (String) -> Unit,

    ) {
    val cardColor = getColor(AppColors.AppColorSet.AppCardBackgroundColor)
    val screenBackgroundColor = getColor(AppColors.AppColorSet.AppScreenBackgroundColor)
    val textColor = getColor(AppColors.AppColorSet.AppSecondTextColor)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),

        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )


    ) {

        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                fontFamily = Tajawal,
                color = textColor
            )

            BasicTextField(
                value = info,
                onValueChange = onTextChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(
                    color = textColor,
                    fontSize = 14.sp,
                    fontFamily = Tajawal
                ),
                decorationBox = { innerTextField ->
                    if (info.isEmpty()) {
                        Text(
                            text = "Enter $title",
                            color = textColor.copy(alpha = 0.6f),
                            fontSize = 9.sp
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}
