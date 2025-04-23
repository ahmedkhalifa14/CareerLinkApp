package com.ahmedkhalifa.careerlinkapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity(tableName = "job_table")
@Serializable
data class Job(
    val category: String?,
    val company_logo: String?,
    val company_logo_url: String?,
    val company_name: String?,
    val description: String?,
    @PrimaryKey
    val id: Int?,
    val job_type: String?,
    val publication_date: String?,
    @SerializedName("candidate_required_location")
    val location: String?,
    val salary: String?,
    val tags: List<String>? = null,
    val title: String?,
    val url: String?,
    val dateAdded: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "saved") val saved: Boolean = false,
    @ColumnInfo(name = "applied") val applied: Boolean = false

)
