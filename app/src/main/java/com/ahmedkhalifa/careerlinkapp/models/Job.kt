package com.ahmedkhalifa.careerlinkapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "job_table")
@Serializable
data class Job(
    @SerialName("candidate_required_location")
    val location: String?,
    val category: String?,
    val company_logo: String?,
    val company_logo_url: String?,
    val company_name: String?,
    val description: String?,
    @PrimaryKey
    val id: Int?,
    val job_type: String?,
    val publication_date: String?,
    val salary: String?,
    val tags: List<String>? = null,
    val title: String?,
    val url: String?,
    val dateAdded: Long = System.currentTimeMillis(),
    val saved : Boolean = false,
    val applied : Boolean = false,

)
