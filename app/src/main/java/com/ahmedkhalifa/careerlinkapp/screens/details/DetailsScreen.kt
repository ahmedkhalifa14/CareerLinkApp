package com.ahmedkhalifa.careerlinkapp.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.models.ParentJob
import com.ahmedkhalifa.careerlinkapp.utils.Event
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.ahmedkhalifa.careerlinkapp.viewmodel.ApiViewModel


@Composable
fun DetailsScreen(
    viewModel: ApiViewModel = hiltViewModel()
) {
    val job = viewModel.remoteJobsState.collectAsState()
    DetailsScreenContent(jobState = job.value)
}

@Composable
fun DetailsScreenContent(
    jobState: Event<Resource<ParentJob<Job>>>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Image(
                modifier = Modifier.size(128.dp),
                painter = rememberAsyncImagePainter(model = jobState.peekContent().data?.jobs?.get(0)?.company_logo_url),
                contentDescription = "Company Logo"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Card {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.save_ic),
                            contentDescription = "Favorite"
                        ) // Replace with your heart icon
                    }
                }
                Spacer(modifier = Modifier.size(4.dp))
                Card {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.save_ic),
                            contentDescription = "Share"
                        ) // Replace with your share icon
                    }
                }


            }
        }
    }
}