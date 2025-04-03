package com.ahmedkhalifa.careerlinkapp.screens.home


import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.composable.JobCategoriesSection
import com.ahmedkhalifa.careerlinkapp.composable.RemoteJobsSection
import com.ahmedkhalifa.careerlinkapp.composable.SearchBar
import com.ahmedkhalifa.careerlinkapp.composable.StickyHeader
import com.ahmedkhalifa.careerlinkapp.composable.TopBar
import com.ahmedkhalifa.careerlinkapp.composable.getColor
import com.ahmedkhalifa.careerlinkapp.models.Category
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.models.ParentJob
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.utils.Event
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.ahmedkhalifa.careerlinkapp.viewmodel.ApiViewModel


@Composable

fun HomePage(
    apiViewModel: ApiViewModel = hiltViewModel()
) {
    val remoteJobsState = apiViewModel.remoteJobsState.collectAsState()
    val remoteJobsCategoriesState = apiViewM\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\;;;odel.remoteJobsCategoriesState.collectAsState()
    LaunchedEffect(key1 = true) {
        apiViewModel.getRemoteJobs(20)
        apiViewModel.getRemoteJobsCategories()
    }
    HomePageContent(
        remoteJobsState = remoteJobsState.value,
        remoteJobsCategoriesState = remoteJobsCategoriesState.value,
        refreshJobs = {
            apiViewModel.loadMoreJobs()
            apiViewModel.getRemoteJobsCategories()
        }
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePageContent(
    remoteJobsState: Event<Resource<ParentJob<Job>>>,
    remoteJobsCategoriesState: Event<Resource<ParentJob<Category>>>,
    refreshJobs: () -> Unit,
    onRemoteJobCardClick: (Job) -> Unit = {}
) {
    val screenBackgroundColor= getColor(AppColors.AppColorSet.AppScreenBackgroundColor)
    val refreshing = remember { mutableStateOf(false) } // Manage refreshing state
    val pullRefreshState = rememberPullRefreshState(refreshing.value, {
        refreshing.value = true
        refreshJobs()
        refreshing.value = false
    })

    Box(Modifier.pullRefresh(pullRefreshState)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(screenBackgroundColor)
        ) {
            TopBar()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                stickyHeader {
                    SearchBar()
                    Spacer(modifier = Modifier.height(16.dp))
                }
                stickyHeader {
                    StickyHeader(title = "Categories")
                }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        item {
                            JobCategoriesSection(
                                categories = remoteJobsCategoriesState.peekContent().data?.jobs,
                                isLoading = remoteJobsCategoriesState.peekContent() is Resource.Loading,
                                errorMessage = (remoteJobsCategoriesState.peekContent() as? Resource.Error)?.message,
                            )
                        }

                    }
                }

                stickyHeader {
                    StickyHeader(title = "Recent Jobs")
                }
                item {
                    RemoteJobsSection(
                        jobs = remoteJobsState.peekContent().data?.jobs,
                        isLoading = remoteJobsState.peekContent() is Resource.Loading,
                        errorMessage = (remoteJobsState.peekContent() as? Resource.Error)?.message,
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing.value,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}


@Composable
fun JobCard(job: Job) {
    Card(modifier = Modifier.width(200.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = job.company_logo_url),
                    contentDescription = ""
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.save_ic),
                        contentDescription = "Favorite"
                    ) // Replace with your heart icon
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            job.title?.let { Text(it, fontWeight = FontWeight.Bold) }
            Text("${job.salary}/m ${job.location}", color = Color.Gray)
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomePage() {
    HomePage()
}