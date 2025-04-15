package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal

@Composable
fun SearchBar(
    enabled: Boolean = false,
    onValueChange: (String) -> Unit,
    value: String,
    onClick: () -> Unit

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(getColor(AppColors.AppColorSet.AppCardBackgroundColor))
            .clickable { onClick() }
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            maxLines = 1,
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Search"
                , tint = getColor(AppColors.AppColorSet.AppMainTextColor)
                )

            },
            placeholder = {
                Text(
                    text = stringResource(R.string.search_here),
                    fontWeight = FontWeight.Normal,
                    color = getColor(AppColors.AppColorSet.AppMainTextColor),
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = Tajawal,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(
                fontFamily = Tajawal,
                fontSize = 16.sp,
                color = getColor(AppColors.AppColorSet.AppMainTextColor)
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
