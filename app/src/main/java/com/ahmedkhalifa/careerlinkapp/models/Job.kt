package com.ahmedkhalifa.careerlinkapp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Job(
    @SerialName("candidate_required_location")
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
