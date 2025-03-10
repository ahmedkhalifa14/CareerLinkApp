package com.ahmedkhalifa.careerlinkapp.data.network

import com.ahmedkhalifa.careerlinkapp.models.ParentJob
import retrofit2.http.GET
import retrofit2.http.Query

interface JobsApiService {

    @GET("remote-jobs")
    suspend fun getRemoteJobs(@Query("limit") page: Int): ParentJob

    @GET("remote-jobs")
    suspend fun searchForJobs(
        @Query("limit") page: Int,
        @Query("search") searchKeyword: String?
    ): ParentJob
}