package com.ahmedkhalifa.careerlinkapp.repo.api

import com.ahmedkhalifa.careerlinkapp.data.network.api.JobsApiService
import com.ahmedkhalifa.careerlinkapp.helper.NetworkHelper
import com.ahmedkhalifa.careerlinkapp.models.Category
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.models.ParentJob
import com.ahmedkhalifa.careerlinkapp.repo.room.RoomDbRepo
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.ahmedkhalifa.careerlinkapp.utils.Utils.tryCatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepoImpl @Inject constructor(
    private val apiService: JobsApiService,
    private val networkHelper: NetworkHelper,
    private val roomDbRepo: RoomDbRepo,
) : ApiRepo {

    override suspend fun getRemoteJobs(limit: Int): Resource<ParentJob<Job>> =
        withContext(Dispatchers.IO) {
            tryCatch {
                if (networkHelper.isConnected()) {
                    val apiResponse = apiService.getRemoteJobs(limit)
                    val remoteJobs = apiResponse.jobs

                    // Fetch saved job IDs from Room
                    val savedJobIds = roomDbRepo.getSavedJobs().data
                        ?.mapNotNull { it.id }
                        ?.toSet() ?: emptySet()

                    // Fetch applied job IDs from Room
                    val appliedJobsIds = roomDbRepo.getAppliedJobs().data
                        ?.mapNotNull { it.id }
                        ?.toSet() ?: emptySet()

                    // Merge saved status into remote jobs
                    val mergedJobs = remoteJobs.map { job ->
                        job.copy(
                            saved = job.id != null && savedJobIds.contains(job.id),
                            applied = job.id != null && appliedJobsIds.contains(job.id)
                        )
                    }
                    // Cache updated jobs in Room
                    roomDbRepo.upsertJobs(mergedJobs)
                    // Return updated API response
                    Resource.Success(apiResponse.copy(jobs = ArrayList(mergedJobs)))
                } else {
                    // Offline: return cached jobs
                    val cachedResult = roomDbRepo.getAllJobs()
                    Resource.Success(
                        ParentJob(
                            legal_notice = "Cached Data",
                            warning = "Offline Mode",
                            job_count = cachedResult.data?.size ?: 0,
                            jobs = cachedResult.data ?: emptyList(),
                            total_job_count = cachedResult.data?.size ?: 0
                        )
                    )
                }
            }
        }

    override suspend fun searchForJobs(
        limit: Int,
        searchKeyword: String?
    ): Resource<ParentJob<Job>> =
        withContext(Dispatchers.IO) {
            tryCatch {
                if (networkHelper.isConnected()) {
                    val apiResponse = apiService.searchForJobs(limit, searchKeyword)

                    // Cache jobs even from search
                    roomDbRepo.upsertJobs(apiResponse.jobs)

                    Resource.Success(apiResponse)
                } else {
                    // Offline: filter from local cache
                    val cachedJobs = roomDbRepo.getAllJobs()
                    val filteredJobs = cachedJobs.data?.filter { job ->
                        job.title?.contains(searchKeyword.orEmpty(), ignoreCase = true) == true ||
                                job.description?.contains(searchKeyword.orEmpty(), ignoreCase = true) == true
                    } ?: emptyList()

                    Resource.Success(
                        ParentJob(
                            legal_notice = "Cached Data",
                            warning = "Offline Mode",
                            job_count = filteredJobs.size,
                            jobs = filteredJobs,
                            total_job_count = filteredJobs.size
                        )
                    )
                }
            }
        }

    override suspend fun getRemoteJobsCategories(): Resource<ParentJob<Category>> =
        withContext(Dispatchers.IO) {
            tryCatch {
                val result = apiService.getRemoteJobsCategories()
                Resource.Success(result)
            }
        }

    override suspend fun getSavedJobs(): Resource<List<Job>> =
        roomDbRepo.getSavedJobs()

    override suspend fun toggleSavedStatus(jobId: Int, saved: Boolean): Resource<Unit> =
        tryCatch {
            roomDbRepo.updateSavedStatus(jobId, saved)
        }
}
