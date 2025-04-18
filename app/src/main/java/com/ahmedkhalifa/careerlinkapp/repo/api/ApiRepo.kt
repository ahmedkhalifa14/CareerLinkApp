package com.ahmedkhalifa.careerlinkapp.repo.api

import com.ahmedkhalifa.careerlinkapp.models.Category
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.models.ParentJob
import com.ahmedkhalifa.careerlinkapp.utils.Resource

interface ApiRepo {
    suspend fun getRemoteJobs(limit: Int): Resource<ParentJob<Job>>
    suspend fun getRemoteJobsCategories(): Resource<ParentJob<Category>>
    suspend fun searchForJobs(limit: Int, searchKeyword: String?): Resource<ParentJob<Job>>
    suspend fun getSavedJobs(): Resource<List<Job>>
    suspend fun toggleSavedStatus(jobId: Int, saved: Boolean): Resource<Unit>
}