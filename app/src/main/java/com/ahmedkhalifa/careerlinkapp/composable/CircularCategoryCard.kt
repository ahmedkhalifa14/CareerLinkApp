package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmedkhalifa.careerlinkapp.models.Category
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppMainColor
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal


@Composable
fun CircularCategoryCard(category: Category) {
    val textColor= getColor(AppColors.AppColorSet.AppMainTextColor)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        // verticalArrangement = Arrangement.spacedBy(4.dp),
        // modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        val firstLetter = category.name.first().toString().uppercase()
        val color = AppMainColor

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = firstLetter,
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontFamily = Tajawal
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = category.name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontFamily = Tajawal,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = textColor,
            modifier = Modifier.width(80.dp)
        )
    }
}