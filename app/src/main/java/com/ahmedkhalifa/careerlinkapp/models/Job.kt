package com.ahmedkhalifa.careerlinkapp.models

import com.google.gson.annotations.SerializedName

data class Job(
    @SerializedName("candidate_required_location")
    val location: String?,
    val category: String?,
    val company_logo: String?,
    val company_logo_url: String?,
    val company_name: String?,
    val description: String?,
    val id: Int?,
    val job_type: String?,
    val publication_date: String?,
    val salary: String?,
    val tags: List<String>? = null,
    val title: String?,
    val url: String?
)