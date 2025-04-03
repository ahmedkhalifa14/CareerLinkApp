package com.ahmedkhalifa.careerlinkapp.models

import com.google.gson.annotations.SerializedName

data class ParentJob<T>(
    @SerializedName("0-legal-notice")
    val legal_notice: String,
    @SerializedName("00-warning")
    val warning: String,
    @SerializedName("jobs-count")
    val job_count: Int,
    val jobs: ArrayList<T>,
    @SerializedName("total-job-count")
    val total_job_count: Int
)