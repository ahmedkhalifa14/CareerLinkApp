package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun ShimmerRemoteJobCard() {
    val shimmerColor = getColor(AppColors.AppColorSet.AppCardBackgroundColor)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(shimmerColor, RoundedCornerShape(8.dp))
                    .placeholder(visible = true)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth(0.6f)
                        .background(shimmerColor, RoundedCornerShape(4.dp))
                        .placeholder(visible = true)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .height(14.dp)
                        .fillMaxWidth(0.4f)
                        .background(shimmerColor, RoundedCornerShape(4.dp))
                        .placeholder(visible = true)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .fillMaxWidth(0.5f)
                        .background(shimmerColor, RoundedCornerShape(4.dp))
                        .placeholder(visible = true)
                )
            }
        }
    }
}