package com.ahmedkhalifa.careerlinkapp.screens.profile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.composable.RemoteJobsSection
import com.ahmedkhalifa.careerlinkapp.composable.getColor
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal
import com.ahmedkhalifa.careerlinkapp.viewmodel.RoomViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ApplicationsScreen(
    navController: NavController,
    roomViewModel: RoomViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        roomViewModel.getAppliedJobs()
    }
    val appliedJobsState = roomViewModel.allAppliedJobsState.collectAsState()

    ApplicationsContent(
        jobList = appliedJobsState.value.peekContent().data,
        onAppliedJobCardClick = { job ->
            val jobJson = Uri.encode(Json.encodeToString(job))
            navController.navigate("information/$jobJson")
        },

        )
}

@Composable
fun ApplicationsContent(
    jobList: List<Job>?,
    onAppliedJobCardClick: (Job) -> Unit = {},
    onJobLongClick: (Job) -> Unit = {},
    onFavoriteClick: (Job) -> Unit = {}
) {
    val screenBackgroundColor = getColor(AppColors.AppColorSet.AppScreenBackgroundColor)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                Text(
                    text = stringResource(R.string.applied_jobs),
                    color = getColor(AppColors.AppColorSet.AppMainTextColor),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Tajawal,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                RemoteJobsSection(
                    jobs = jobList,
                    isLoading = false,
                    errorMessage = null,
                    onJobClick = onAppliedJobCardClick,
                    onJobLongClick = onJobLongClick,
                    onFavoriteClick = onFavoriteClick,
                    viewFavIcon = false,
                    )
            }
        }
    }
}
