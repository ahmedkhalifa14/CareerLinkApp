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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.placeholder
import coil.compose.rememberAsyncImagePainter
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.models.ParentJob
import com.ahmedkhalifa.careerlinkapp.utils.Event
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.ahmedkhalifa.careerlinkapp.utils.getDefault
import com.ahmedkhalifa.careerlinkapp.utils.truncate
import com.ahmedkhalifa.careerlinkapp.viewmodel.ApiViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.google.accompanist.placeholder.material.placeholder


@Composable

fun HomePage(
    apiViewModel: ApiViewModel = hiltViewModel()
) {
    val remoteJobsState = apiViewModel.remoteJobsState.collectAsState()
    LaunchedEffect(key1 = true) {
        apiViewModel.getRemoteJobs(5)
    }
    HomePageContent(
        remoteJobsState = remoteJobsState.value,
        refreshJobs = { apiViewModel.getRemoteJobs(20) }
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePageContent(
    remoteJobsState: Event<Resource<ParentJob>>,
    refreshJobs: () -> Unit
) {
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
                    StickyHeader(title = "Recent Posts")
                }
                item {
                    RecentPostsSection(
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
fun RecentPostsSection(
    jobs: List<Job>?,
    isLoading: Boolean,
    errorMessage: String?,
) {
    Column {
        if (isLoading) {
            repeat(5) { ShimmerPostCard() }
        } else if (errorMessage != null) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Error loading jobs: $errorMessage")
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else if (jobs != null) {

            jobs.forEach { job ->
                PostCard(job = job)
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else {

            Text("No jobs available.")
        }
    }
}

@Composable
fun StickyHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text("Show All", color = Color.Gray)
    }
}


@Composable

fun PostCard(job: Job) {
    Card(modifier = Modifier.fillMaxWidth()) {
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
                job.title?.let { Text(it, fontWeight = FontWeight.Bold, maxLines = 1) }
                job.job_type?.let { Text(it.truncate(20), color = Color.Gray) }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.dollar_money_sign_icon), // Replace with your money icon
                        contentDescription = "Salary Icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(job.salary.getDefault(), color = Color.Gray)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.location_ic), // Replace with your location icon
                        contentDescription = "Location Icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(job.location?.truncate(20).getDefault(), color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun ShimmerPostCard() {
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
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .placeholder(visible = true)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth(0.6f)
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                        .placeholder(visible = true)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .height(14.dp)
                        .fillMaxWidth(0.4f)
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                        .placeholder(visible = true)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .fillMaxWidth(0.5f)
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                        .placeholder(visible = true)
                )
            }
        }
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

@Composable
fun SearchBar() {
    OutlinedTextField(value = "", onValueChange = {}, leadingIcon = {
        Icon(Icons.Filled.Search, contentDescription = "Search")
    }, placeholder = { Text(text = "Search Here...") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Menu"
            ) // Replace with your menu icon
        }

        Image(
            painter = painterResource(id = R.drawable.profile_pic), // Replace with your profile pic
            contentDescription = "Profile",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomePage() {
    HomePage()
}