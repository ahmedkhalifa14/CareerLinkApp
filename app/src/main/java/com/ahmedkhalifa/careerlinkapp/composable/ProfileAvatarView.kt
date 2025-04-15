package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors


@Composable
fun ProfileAvatarView(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    size: Int = 128,
    contentScale: ContentScale = ContentScale.Fit,
    onClick: () -> Unit = {}
) {
    val defaultImage = painterResource(R.drawable.profile_filled_icon)
    val painter = rememberAsyncImagePainter(
        model = if (!imageUrl.isNullOrEmpty()) imageUrl else null,
        placeholder = defaultImage,
        error = defaultImage,
        fallback = defaultImage
    )
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                1.dp,
                color = getColor(AppColors.AppColorSet.AppMainTextColor),
                shape = RoundedCornerShape(16.dp)
            ).clickable{
                onClick()
            }

    ) {
        Image(
            painter = painter,
            modifier = modifier.fillMaxSize(),
            contentDescription = "profile image",
            contentScale = contentScale
        )
    }
}
