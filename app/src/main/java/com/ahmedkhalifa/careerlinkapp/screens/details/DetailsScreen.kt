package com.ahmedkhalifa.careerlinkapp.screens.details

import android.os.Build
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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.ahmedkhalifa.careerlinkapp.utils.timeSince
import com.ahmedkhalifa.careerlinkapp.viewmodel.RoomViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreen(
    navController: NavController,
    job: Job?,
    roomViewModel: RoomViewModel = hiltViewModel()
) {
    val jobExistState = roomViewModel.isJobSavedState.collectAsState()

    LaunchedEffect(job?.id) {
        job?.id?.let { roomViewModel.checkIfJobExists(it) }
    }
    val isJobSaved = jobExistState.value.peekContent().data ?: false

    DetailsScreenContent(
        jobState = job,
        isJobSaved = isJobSaved,
        onClickSave = { job ->
            if (isJobSaved){
                job.id?.let { roomViewModel.deleteJob(it)
                roomViewModel.checkIfJobExists(job.id)
                }
            }else{
                roomViewModel.insertJob(job)
                job.id?.let { roomViewModel.checkIfJobExists(it) }
            }
        }
        ,
        onClickApply = {

        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreenContent(
    jobState: Job? = null,
    isJobSaved: Boolean = false,
    onClickSave: (Job) -> Unit = {},
    onClickApply : (Job) -> Unit = {},
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
        val (companyLogo,
            cards,
            jobDetails
        ) = createRefs()
        Image(
            modifier = Modifier
                .size(114.dp)
                .padding(8.dp)
                .constrainAs(companyLogo) {
                    top.linkTo(guideline)
                    start.linkTo(parent.start)
                },
            painter = rememberAsyncImagePainter(
                model = jobState!!.company_logo_url,
                placeholder = painterResource(id = R.drawable.app_logo),
                error = painterResource(id = R.drawable.app_logo)
            ),
            contentDescription = "Company Logo",
        )
        Row(
            modifier = Modifier.constrainAs(cards) {
                top.linkTo(guideline)
                end.linkTo(parent.end)
            },
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(modifier = Modifier.background(cardBackgroundColor)) {
                IconButton(onClick = { onClickSave(jobState) }) {
                    Icon(
                        painter = painterResource(id = if (isJobSaved) R.drawable.marked_ic else R.drawable.not_marked_ic),
                        contentDescription = "Favorite"
                    )
                }
            }

            Card(modifier = Modifier.background(cardBackgroundColor)) {
                IconButton(onClick = { /*TODO*/ }) {
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
            jobState.title?.let {
                Text(
                    text = it,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Card(
                modifier = Modifier
                    .background(cardBackgroundColor),
                shape = RoundedCornerShape(8.dp)

            ) {
                jobState.category?.let {
                    DetailsScreenTextView(
                        text = it,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            jobState.company_name?.let {
                DetailsScreenTextView(
                    text = it,
                    fontWeight = FontWeight.Medium
                )
            }
            jobState.location?.let {
                DetailsScreenTextView(
                    text = it,
                    fontWeight = FontWeight.Medium
                )
            }
            jobState.publication_date?.let { timeSince(it) }?.let {
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
                onClick = {}) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Apply For Job",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = textColor
                )
            }
            DetailsScreenTextView(
                text = "Job Details ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            DetailsScreenTextView(text = "Job Type: ", fontWeight = FontWeight.Light)
            jobState.job_type?.let {
                DetailsScreenTextView(
                    text = it,
                    fontWeight = FontWeight.SemiBold
                )
            }
            DetailsScreenTextView(text = "Salary: ", fontWeight = FontWeight.Light)
            jobState.salary?.let {
                DetailsScreenTextView(
                    text = it,
                    fontWeight = FontWeight.SemiBold
                )
            }
            jobState.tags?.let {
                SkillsAndToolsCard(skills = it) {

                }
            }
            DetailsScreenTextView(
                text = "Job Description ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            jobState.description?.let {
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
    DetailsScreenContent(null)
}
