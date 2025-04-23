package com.ahmedkhalifa.careerlinkapp.data.local.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ahmedkhalifa.careerlinkapp.models.Job

@Dao
interface JobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: Job): Long

    @Query("SELECT * FROM job_table ORDER BY dateAdded DESC")
    suspend fun getAllJobs(): List<Job>

    @Query("SELECT * FROM job_table WHERE saved = 1 ORDER BY dateAdded DESC")
    suspend fun getSavedJobs(): List<Job>

    @Query("SELECT * FROM job_table WHERE applied = 1 ORDER BY dateAdded DESC")
    suspend fun getAppliedJobs(): List<Job>

    @Query("SELECT * FROM job_table")
    suspend fun getAllJobsOnce(): List<Job>

    @Query("UPDATE job_table SET saved = :saved WHERE id = :jobId")
    suspend fun updateSavedStatus(jobId: Int, saved: Boolean)

    @Query("UPDATE job_table SET applied = :applied WHERE id = :jobId")
    suspend fun updateAppliedStatus(jobId: Int, applied: Boolean)

    @Query("DELETE FROM job_table WHERE id = :jobId")
    suspend fun deleteJob(jobId: Int): Int

    @Query("DELETE FROM job_table")
    suspend fun deleteAllJobs(): Int

    @Transaction
    suspend fun upsertJobs(jobs: List<Job>) {
        val localJobs = getAllJobsOnce().associateBy { it.id }

        val merged = jobs.map { remote ->
            val local = localJobs[remote.id]
            remote.copy(
                saved = local?.saved ?: false,
                applied = local?.applied ?: false,
                dateAdded = System.currentTimeMillis()
            )
        }
        merged.forEach {
            insertJob(it)
        }
    }
}

