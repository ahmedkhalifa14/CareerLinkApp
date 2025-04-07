package com.ahmedkhalifa.careerlinkapp.repo

import com.ahmedkhalifa.careerlinkapp.data.network.api.JobsApiService
import com.ahmedkhalifa.careerlinkapp.models.Category
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.models.ParentJob
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.ahmedkhalifa.careerlinkapp.utils.Utils.tryCatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepoImpl
@Inject constructor(
    private val apiService: JobsApiService
) : ApiRepo {

    override suspend fun getRemoteJobs(limit: Int): Resource<ParentJob<Job>> =
        withContext(Dispatchers.IO) {
            tryCatch {
                val result = apiService.getRemoteJobs(limit)
                Resource.Success(result)
            }
        }

    override suspend fun getRemoteJobsCategories(): Resource<ParentJob<Category>> =
        withContext(Dispatchers.IO) {
            tryCatch {
                val result = apiService.getRemoteJobsCategories()
                Resource.Success(result)
            }
        }



    override suspend fun searchForJobs(limit: Int, searchKeyword: String?): Resource<ParentJob<Job>> =
        withContext(Dispatchers.IO) {
            tryCatch {
                val result = apiService.searchForJobs(limit, searchKeyword)
                Resource.Success(result)
            }
        }
}
