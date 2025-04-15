package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal

@Composable
fun SettingsItem(
    title: String, subtitle: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = getColor(AppColors.AppColorSet.AppCardBackgroundColor),
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Tajawal,
                    fontSize = 16.sp,
                    color = getColor(AppColors.AppColorSet.AppMainTextColor),
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    fontFamily = Tajawal,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = getColor(AppColors.AppColorSet.AppSecondTextColor),
                )
            }

            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.padding(start = 8.dp),

            )
        }
    }
}
