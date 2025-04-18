
package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.models.Job

@Composable
fun RemoteJobsSection(
    jobs: List<Job>?,
    isLoading: Boolean,
    errorMessage: String?,
    onJobClick: (Job) -> Unit,
    onJobLongClick: (Job) -> Unit = {},
    onFavoriteClick: (Job) -> Unit,
) {
    Column(
        modifier = Modifier
    ) {
        if (isLoading) {
            repeat(5) { ShimmerRemoteJobCard() }
        }
        else if (errorMessage != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(stringResource(R.string.error_loading_jobs, errorMessage))
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        else if (jobs != null) {
            jobs.forEach { job ->
                RemoteJobCard(
                    job = job,
                    onClick = { onJobClick(job) },
                    onLongClick = { onJobLongClick(job) },
                    onFavoriteClick = { onFavoriteClick(job) },
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        else {
            Text(stringResource(R.string.no_jobs_available))
        }
    }
}
