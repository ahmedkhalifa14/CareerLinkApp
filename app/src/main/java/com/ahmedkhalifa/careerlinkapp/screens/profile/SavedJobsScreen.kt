package com.ahmedkhalifa.careerlinkapp.screens.profile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.ahmedkhalifa.careerlinkapp.viewmodel.RoomViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun SavedJobsScreen(
    navController: NavController,
    viewModel: RoomViewModel = hiltViewModel(),
) {
    val jobListState = remember { mutableStateListOf<Job>() }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getSavedJobs()
    }
    val savedJobsState = viewModel.allJobState.collectAsState()

    LaunchedEffect(savedJobsState.value) {
        val result = savedJobsState.value.peekContent()
        if (result is Resource.Success) {
            jobListState.clear()
            jobListState.addAll(result.data ?: emptyList())
        }
    }

    val onFavoriteClick: (Job) -> Unit = { job ->
        viewModel.updateSavedStatus(job.id!!, false)
        jobListState.removeIf { it.id == job.id }
    }
    if (showDeleteDialog && selectedJob != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                selectedJob = null
            },
            title = { Text(stringResource(R.string.delete_job)) },
            text = { Text(stringResource(R.string.are_you_sure_you_want_to_delete_this_job_from_saved)) },
            confirmButton = {
                TextButton(onClick = {
                    selectedJob?.id?.let {
                        viewModel.deleteJob(it)
                        jobListState.removeIf { job -> job.id == it }
                    }
                    showDeleteDialog = false
                    selectedJob = null
                }) {
                    Text(stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    selectedJob = null
                }) {
                    Text(stringResource(R.string.no))
                }
            }
        )
    }

    SavedJobsScreenContent(
        jobList = jobListState,
        onSavedJobCardClick = { job ->
            val jobJson = Uri.encode(Json.encodeToString(job))
            navController.navigate("information/$jobJson")
        },
        onJobLongClick = {
            selectedJob = it
            showDeleteDialog = true
        },
        onFavoriteClick = onFavoriteClick
    )
}


@Composable
fun SavedJobsScreenContent(
    jobList: List<Job>,
    onSavedJobCardClick: (Job) -> Unit = {},
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
                    text = stringResource(R.string.saved_jobs),
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
                    onJobClick = onSavedJobCardClick,
                    onJobLongClick = onJobLongClick,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}
