package com.ahmedkhalifa.careerlinkapp.models

import com.google.gson.annotations.SerializedName

data class ParentJob<T>(
    @SerializedName("0-legal-notice")
    val legal_notice: String="",
    @SerializedName("00-warning")
    val warning: String="",
    @SerializedName("jobs-count")
    val job_count: Int = 0,
    val jobs: List<T> = emptyList(),
    @SerializedName("total-job-count")
    val total_job_count: Int = 0
)