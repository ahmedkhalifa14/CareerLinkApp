package com.ahmedkhalifa.careerlinkapp.screens.home


import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.composable.JobCategoriesSection
import com.ahmedkhalifa.careerlinkapp.composable.RemoteJobsSection
import com.ahmedkhalifa.careerlinkapp.composable.SearchBar
import com.ahmedkhalifa.careerlinkapp.composable.StickyHeader
import com.ahmedkhalifa.careerlinkapp.composable.TopBar
import com.ahmedkhalifa.careerlinkapp.composable.getColor
import com.ahmedkhalifa.careerlinkapp.graphs.SearchScreen
import com.ahmedkhalifa.careerlinkapp.models.Category
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.models.ParentJob
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.utils.Event
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.ahmedkhalifa.careerlinkapp.viewmodel.ApiViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun HomePage(
    navigationController: NavController? = null,
    apiViewModel: ApiViewModel = hiltViewModel(),
    listState: LazyListState,
    isScrollingDown: Boolean
) {
    val remoteJobsState = apiViewModel.remoteJobsState.collectAsState()
    val remoteJobsCategoriesState = apiViewModel.remoteJobsCategoriesState.collectAsState()

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
        },
        onRemoteJobCardClick = { job ->
            val jobJson = Uri.encode(Json.encodeToString(job))
            navigationController!!.navigate("information/$jobJson")
        },
        listState = listState,
        isScrollingDown = isScrollingDown,
        onSearchClick = {
            navigationController!!.navigate(SearchScreen.Search.route)
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
    onRemoteJobCardClick: (Job) -> Unit = {},
    listState: LazyListState,
    isScrollingDown: Boolean,
    onSearchClick: () -> Unit,
) {
    val screenBackgroundColor = getColor(AppColors.AppColorSet.AppScreenBackgroundColor)
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
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                stickyHeader {
                    SearchBar(false,{},"",onSearchClick)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                stickyHeader {
                    StickyHeader(title = stringResource(R.string.categories))
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
                    StickyHeader(title = stringResource(R.string.recent_jobs))
                }
                item {
                    RemoteJobsSection(
                        jobs = remoteJobsState.peekContent().data?.jobs,
                        isLoading = remoteJobsState.peekContent() is Resource.Loading,
                        errorMessage = (remoteJobsState.peekContent() as? Resource.Error)?.message,
                        onJobClick = onRemoteJobCardClick
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
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewHomePage() {
//    HomePage(listState = listState, isScrollingDown = isScrollingDown)
//}
