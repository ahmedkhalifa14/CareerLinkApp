package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.utils.getDefault
import com.ahmedkhalifa.careerlinkapp.utils.truncate

@Composable

fun RemoteJobCard(job: Job, onClick: () -> Unit) {
    val cardBackgroundColor = getColor(AppColors.AppColorSet.AppCardBackgroundColor)
    val textColor = getColor(AppColors.AppColorSet.AppMainTextColor)
    val textSecondColor = getColor(AppColors.AppColorSet.AppSecondTextColor)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor
        )
    )

    {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
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

            Column {
                job.title?.let {
                    Text(
                        it,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        color = textColor
                    )
                }
                job.job_type?.let { Text(it.truncate(20), color = textSecondColor) }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.dollar_money_sign_icon), // Replace with your money icon
                        contentDescription = "Salary Icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(job.salary.getDefault(), maxLines = 1, color = textSecondColor)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.location_ic),
                        contentDescription = "Location Icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(job.location?.truncate(20).getDefault(), color = textSecondColor)
                }
            }
        }
    }
}