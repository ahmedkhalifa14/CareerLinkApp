package com.ahmedkhalifa.careerlinkapp.repo

import com.ahmedkhalifa.careerlinkapp.models.ParentJob
import com.ahmedkhalifa.careerlinkapp.utils.Resource

interface ApiRepo {
    suspend fun getRemoteJobs(limit: Int): Resource<ParentJob>
    suspend fun searchForJobs(
        limit: Int,
        searchKeyword: String?
    ): Resource<ParentJob>
}