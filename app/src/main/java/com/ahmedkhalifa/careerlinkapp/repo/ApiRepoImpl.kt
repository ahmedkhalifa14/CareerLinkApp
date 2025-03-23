package com.ahmedkhalifa.careerlinkapp.repo

import android.util.Log
import com.ahmedkhalifa.careerlinkapp.data.network.JobsApiService
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

    override suspend fun getRemoteJobs(limit: Int): Resource<ParentJob> =
        withContext(Dispatchers.IO) {
            tryCatch {
                Log.d("API_REQUEST", "Fetching jobs with limit: $limit")
                val result = apiService.getRemoteJobs(limit)
                Resource.Success(result)
            }
        }


    override suspend fun searchForJobs(limit: Int, searchKeyword: String?): Resource<ParentJob> =
        withContext(Dispatchers.IO) {
            tryCatch {
                val result = apiService.searchForJobs(limit, searchKeyword)
                Resource.Success(result)
            }
        }
}
