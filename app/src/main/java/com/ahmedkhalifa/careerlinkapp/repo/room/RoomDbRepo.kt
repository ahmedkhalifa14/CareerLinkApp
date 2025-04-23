package com.ahmedkhalifa.careerlinkapp.repo.room

import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.utils.Resource

interface RoomDbRepo {
    suspend fun insertJob(job: Job):Resource< Long>
    suspend fun getAllJobs():Resource< List<Job>>
    suspend fun deleteJob(jobId: Int):Resource <Int>
    suspend fun deleteAllJobs(): Resource<Int>
    suspend fun upsertJobs(jobs: List<Job>): Resource<Unit>
    suspend fun getSavedJobs(): Resource<List<Job>>
    suspend fun getAppliedJobs(): Resource<List<Job>>
    suspend fun updateSavedStatus(jobId: Int, saved: Boolean): Resource<Unit>
    suspend fun updateAppliedStatus(jobId: Int, applied: Boolean): Resource<Unit>
}