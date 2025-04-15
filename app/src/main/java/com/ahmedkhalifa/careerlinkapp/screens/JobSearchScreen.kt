package com.ahmedkhalifa.careerlinkapp.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahmedkhalifa.careerlinkapp.composable.JobItem
import com.ahmedkhalifa.careerlinkapp.composable.SearchBar
import com.ahmedkhalifa.careerlinkapp.composable.getColor
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.utils.EventObserver
import com.ahmedkhalifa.careerlinkapp.viewmodel.ApiViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun JobSearchScreen(viewModel: ApiViewModel = hiltViewModel(), navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    var searchText by rememberSaveable { mutableStateOf("") }
    var jobs by remember { mutableStateOf<List<Job>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.searchJobsState.collect(
            EventObserver(
                onLoading = {
                    isLoading = true
                },
                onError = { message ->
                    isLoading = false
                    errorMessage = message
                },
                onSuccess = { data ->
                    isLoading = false
                    jobs = data.jobs
                }
            )
        )
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar("Error: $it")
            errorMessage = null
        }
    }

    LaunchedEffect(searchText) {
        if (searchText.isBlank()) {
            jobs = emptyList()
        } else {
            viewModel.searchForJobs(10, searchText)
        }
    }

    JobSearchScreenContent(
        searchText = searchText,
        onSearchTextChanged = { searchText = it },
        jobs = jobs,
        isLoading = isLoading,
        snackbarHostState = snackbarHostState,
        onBackPress = { navController.popBackStack() },
        onJobCardClick = { job->
            val jobJson = Uri.encode(Json.encodeToString(job))
            navController.navigate("information/$jobJson")
        }
    )
}

@Composable
fun JobSearchScreenContent(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    jobs: List<Job>,
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState,
    onBackPress: () -> Unit,
    onJobCardClick: (Job) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(getColor(AppColors.AppColorSet.AppScreenBackgroundColor))
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackPress,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
                    tint = getColor(AppColors.AppColorSet.AppMainTextColor)
                )
            }


            SearchBar(
                enabled = true,
                onValueChange = onSearchTextChanged,
                value = searchText
            ) {}
        }

        SnackbarHost(hostState = snackbarHostState)
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn {
                    items(jobs) { job ->
                        JobItem(job = job,onClick={onJobCardClick(job)})
                    }
                }
            }
        }
    }
}

