package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp


@Composable
fun ProfileAvatarView(modifier: Modifier = Modifier, painter: Painter, size: Int = 128) {
    Image(
        painter = painter,
        modifier = modifier
            .size(size.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentDescription = "profile image"
    )
}