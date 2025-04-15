package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppMainColor
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = remember { FocusRequester() }
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = AppMainColor,
            unfocusedIndicatorColor = getColor(AppColors.AppColorSet.AppSecondTextColor),
            errorIndicatorColor = Color.Red,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Red.copy(alpha = 0.1f)
        ),
        label = {
            Text(
                text = label, fontWeight = FontWeight.SemiBold, fontFamily = Tajawal
            )
        },
        placeholder = {
            Text(
                text = placeholder, fontFamily = Tajawal, fontWeight = FontWeight.Normal
            )
        },
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = "Input Icon", tint = Color.Gray)
        },
        maxLines = 1,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}
