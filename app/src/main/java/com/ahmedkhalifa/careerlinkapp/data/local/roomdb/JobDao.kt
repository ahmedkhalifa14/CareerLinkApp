package com.ahmedkhalifa.careerlinkapp.data.local.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmedkhalifa.careerlinkapp.models.Job


@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: Job):Long
    @Query("select * from job_table order by dateAdded asc")
    suspend fun getAllJobs(): List<Job>
    @Query("delete from job_table where id = :jobId")
    suspend fun deleteJob(jobId: Int): Int
    @Query("delete from job_table")
    suspend fun deleteAllJobs(): Int
    @Query("SELECT EXISTS(SELECT 1 FROM job_table WHERE id = :jobId LIMIT 1)")
    suspend fun doesJobExist(jobId: Int): Boolean
}