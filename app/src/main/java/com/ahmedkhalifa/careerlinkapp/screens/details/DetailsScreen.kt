package com.ahmedkhalifa.careerlinkapp.screens.details

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.composable.DetailsScreenTextView
import com.ahmedkhalifa.careerlinkapp.composable.SkillsAndToolsCard
import com.ahmedkhalifa.careerlinkapp.composable.getColor
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppMainColor
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal
import com.ahmedkhalifa.careerlinkapp.utils.EventObserver
import com.ahmedkhalifa.careerlinkapp.utils.timeSince
import com.ahmedkhalifa.careerlinkapp.viewmodel.NotificationViewModel
import com.ahmedkhalifa.careerlinkapp.viewmodel.RoomViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreen(
    navController: NavController,
    job: Job?,
    roomViewModel: RoomViewModel = hiltViewModel(),
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    Log.d("DetailsScreen", "DetailsScreen: $job")
    val isJobSaved = rememberSaveable { mutableStateOf(job?.saved ?: false) }
    val isJobApplied = rememberSaveable { mutableStateOf(job?.applied ?: false) }
    val notificationState = notificationViewModel.sendNotificationState.collectAsState()
    val context = LocalContext.current
    var hasTriggeredNotification by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            job?.let { notificationViewModel.sendNotification(it) }
        } else {
            Toast.makeText(context, "Please enable notification permission", Toast.LENGTH_LONG)
                .show()
        }
    }

    LaunchedEffect(notificationState.value) {
        if (hasTriggeredNotification) {
            notificationViewModel.sendNotificationState.collect(
                EventObserver<Boolean>(
                    onLoading = {

                    },
                    onError = {
                        Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                        }
                    },
                    onSuccess = {
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                    }
                )
            )
        }
    }

    DetailsScreenContent(
        jobState = job,
        isJobSaved = isJobSaved.value,
        isJobApplied = isJobApplied.value,
        onClickSave = { job ->
            job.let {
                val newSavedState = !isJobSaved.value
                roomViewModel.updateSavedStatus(it.id ?: return@let, newSavedState)
                isJobSaved.value = newSavedState
            }
        },
        onClickApply = { job ->
            job.let {
                val newAppliedState = !isJobApplied.value
                roomViewModel.updateAppliedStatus(it.id!!, newAppliedState)
                hasTriggeredNotification = true
                notificationViewModel.sendNotification(it)
                isJobApplied.value = newAppliedState
            }
        }

    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreenContent(
    jobState: Job? = null,
    isJobSaved: Boolean = jobState?.saved ?: false,
    isJobApplied: Boolean = jobState?.applied ?: false,
    onClickSave: (Job) -> Unit = {},
    onClickApply: (Job) -> Unit = {}
) {
    val screenBackgroundColor = getColor(AppColors.AppColorSet.AppScreenBackgroundColor)
    val textColor = getColor(AppColors.AppColorSet.AppMainTextColor)
    val cardBackgroundColor = getColor(AppColors.AppColorSet.AppCardBackgroundColor)

    ConstraintLayout(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(screenBackgroundColor)
            .padding(16.dp)
    ) {
        val guideline = createGuidelineFromTop(32.dp)
        val (companyLogo, cards, jobDetails) = createRefs()

        Image(
            modifier = Modifier
                .size(114.dp)
                .padding(8.dp)
                .constrainAs(companyLogo) {
                    top.linkTo(guideline)
                    start.linkTo(parent.start)
                },
            painter = rememberAsyncImagePainter(
                model = jobState?.company_logo_url,
                placeholder = painterResource(id = R.drawable.app_logo),
                error = painterResource(id = R.drawable.app_logo)
            ),
            contentDescription = "Company Logo"
        )

        Row(
            modifier = Modifier.constrainAs(cards) {
                top.linkTo(guideline)
                end.linkTo(parent.end)
            },
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.background(cardBackgroundColor),
                colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
            ) {
                IconButton(onClick = { jobState?.let { onClickSave(it) } }) {
                    Icon(
                        painter = painterResource(id = if (isJobSaved) R.drawable.marked_ic else R.drawable.not_marked_ic),
                        contentDescription = "Favorite"
                    )
                }
            }

            Card(
                modifier = Modifier.background(cardBackgroundColor),
                colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.share_ic),
                        contentDescription = "Share"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(jobDetails) {
                    top.linkTo(companyLogo.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            jobState?.title?.let {
                Text(
                    text = it,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Tajawal,
                    color = textColor
                )
            }
            Card(
                modifier = Modifier.background(cardBackgroundColor),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
            ) {
                jobState?.category?.let {
                    DetailsScreenTextView(
                        text = it,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            jobState?.company_name?.let {
                DetailsScreenTextView(
                    text = it,
                    fontWeight = FontWeight.Medium
                )
            }
            jobState?.location?.let {
                DetailsScreenTextView(
                    text = it,
                    fontWeight = FontWeight.Medium
                )
            }
            jobState?.publication_date?.let { timeSince(it) }?.let {
                DetailsScreenTextView(
                    text = it,
                    fontWeight = FontWeight.Light
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppMainColor,
                    contentColor = Color.White
                ),
                enabled = !isJobApplied,
                onClick = { jobState?.let { onClickApply(it) } }
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = stringResource(R.string.apply_for_job),
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = Tajawal
                )
            }
            DetailsScreenTextView(
                text = "Job Details",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            DetailsScreenTextView(
                text = stringResource(R.string.job_type),
                fontWeight = FontWeight.Light
            )
            jobState?.job_type?.let {
                DetailsScreenTextView(
                    text = it,
                    fontWeight = FontWeight.SemiBold
                )
            }
            DetailsScreenTextView(
                text = stringResource(R.string.salary),
                fontWeight = FontWeight.Light
            )
            jobState?.salary?.let {
                DetailsScreenTextView(
                    text = it,
                    fontWeight = FontWeight.SemiBold
                )
            }
            jobState?.tags?.let {
                SkillsAndToolsCard(skills = it) {}
            }
            DetailsScreenTextView(
                text = stringResource(R.string.job_description),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            jobState?.description?.let {
                DetailsScreenTextView(
                    text = it,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDetailsScreen() {
    DetailsScreenContent()
}