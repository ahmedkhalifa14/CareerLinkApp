package com.ahmedkhalifa.careerlinkapp.repo

import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.utils.Resource

interface RoomDbRepo {
    suspend fun insertJob(job: Job):Resource< Long>
    suspend fun getAllJobs():Resource< List<Job>>
    suspend fun deleteJob(jobId: Int):Resource <Int>
    suspend fun deleteAllJobs(): Resource<Int>
    suspend fun doesJobExist(jobId: Int): Resource<Boolean>
}