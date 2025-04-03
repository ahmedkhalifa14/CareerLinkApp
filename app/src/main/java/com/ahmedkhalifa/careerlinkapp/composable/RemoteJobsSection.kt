package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ahmedkhalifa.careerlinkapp.models.Job

@Composable
fun RemoteJobsSection(
    jobs: List<Job>?,
    isLoading: Boolean,
    errorMessage: String?,
) {
    Column(
        modifier = Modifier
    ) {
        if (isLoading) {
            repeat(5) { ShimmerRemoteJobCard() }
        } else if (errorMessage != null) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Error loading jobs: $errorMessage")
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else if (jobs != null) {

            jobs.forEach { job ->
                RemoteJobCard(job = job)
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else {

            Text("No jobs available.")
        }
    }
}
