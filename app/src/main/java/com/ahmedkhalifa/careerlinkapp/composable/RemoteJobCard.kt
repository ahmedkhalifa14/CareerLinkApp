package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppMainColor
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal
import com.ahmedkhalifa.careerlinkapp.utils.getDefault
import com.ahmedkhalifa.careerlinkapp.utils.truncate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RemoteJobCard(
    job: Job,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {},
    onFavoriteClick: () -> Unit,
    viewFavIcon: Boolean = true,
) {
    val cardBackgroundColor = getColor(AppColors.AppColorSet.AppCardBackgroundColor)
    val textColor = getColor(AppColors.AppColorSet.AppMainTextColor)
    val textSecondColor = getColor(AppColors.AppColorSet.AppSecondTextColor)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick, // Main job click action (e.g., navigate to details)
                onLongClick = onLongClick// No action for long click, as you're using it elsewhere
            ),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Company Logo
            Image(
                painter = rememberAsyncImagePainter(
                    model = job.company_logo_url?.ifBlank { R.drawable.app_logo },
                    placeholder = painterResource(id = R.drawable.app_logo),
                    error = painterResource(id = R.drawable.app_logo),
                ),
                contentDescription = "Company Logo",
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Job title
                job.title?.let {
                    Text(
                        it,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Tajawal,
                        maxLines = 1,
                        color = textColor
                    )
                }
                // Job type
                job.job_type?.let {
                    Text(
                        it.truncate(20),
                        color = textSecondColor,
                        fontWeight = FontWeight.Normal,
                        fontFamily = Tajawal
                    )
                }
                // Salary
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(R.drawable.dollar_money_sign_icon),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        job.salary.getDefault(),
                        maxLines = 1,
                        color = textSecondColor,
                        fontWeight = FontWeight.Normal,
                        fontFamily = Tajawal
                    )
                }
                // Location
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(R.drawable.location_ic),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        job.location?.truncate(20).getDefault(),
                        color = textSecondColor,
                        fontWeight = FontWeight.Normal,
                        fontFamily = Tajawal
                    )
                }
            }

            // Favorite Icon Button
            if (viewFavIcon) {
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        painter = painterResource(
                            id = if (job.saved) R.drawable.marked_ic else R.drawable.not_marked_ic
                        ),
                        contentDescription = "Favorite Icon",
                        tint = if (job.saved) Color.Red else Color.Gray
                    )
                }
            } else {
                Text(
                    "Applied",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontFamily = Tajawal,
                    color = AppMainColor
                )
            }


        }
    }
}
