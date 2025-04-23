package com.ahmedkhalifa.careerlinkapp.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.composable.getColor
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppMainColor
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal

@Composable
fun ApplicationStatusScreen(
    navController: NavController,
    jobId: String?,
    jobTitle: String?
) {
    val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(getColor(AppColors.AppColorSet.AppScreenBackgroundColor))
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.application_ic),
                    contentDescription = null,
                    tint = AppMainColor,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = stringResource(R.string.application_status),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Tajawal,
                    color = getColor(AppColors.AppColorSet.AppMainTextColor)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = getColor(AppColors.AppColorSet.AppCardBackgroundColor)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = stringResource(
                                R.string.job_title,
                                jobTitle ?: context.getString(R.string.unknown)
                            ),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Tajawal,
                            color = getColor(AppColors.AppColorSet.AppMainTextColor)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(
                                R.string.job_id,
                                jobId ?: context.getString(R.string.unknown)
                            ),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Tajawal,
                            color = getColor(AppColors.AppColorSet.AppMainTextColor)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = stringResource(R.string.pending_review),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Tajawal,
                            color = getColor(AppColors.AppColorSet.AppSecondTextColor)
                        )
                    }
                }
            }

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppMainColor,
                    contentColor = getColor(AppColors.AppColorSet.AppMainTextColor)
                )
            ) {
                Text(
                    text = stringResource(R.string.back),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    fontFamily = Tajawal,
                    color = getColor(AppColors.AppColorSet.AppMainTextColor)
                )
            }
        }
    }

