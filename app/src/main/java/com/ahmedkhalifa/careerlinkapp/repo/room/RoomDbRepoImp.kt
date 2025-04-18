package com.ahmedkhalifa.careerlinkapp.repo.room

import com.ahmedkhalifa.careerlinkapp.data.local.roomdb.JobDao
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.ahmedkhalifa.careerlinkapp.utils.Utils.tryCatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomDbRepoImpl @Inject constructor(
    private val jobDao: JobDao
) : RoomDbRepo {
    override suspend fun insertJob(job: Job): Resource<Long> =
        withContext(Dispatchers.IO) {
            tryCatch {
                val result = jobDao.insertJob(job)
                Resource.Success(result)
            }
        }

    override suspend fun getAllJobs(): Resource<List<Job>> =
        withContext(Dispatchers.IO) {
            tryCatch {
                val result = jobDao.getAllJobs()
                Resource.Success(result)
            }
        }

    override suspend fun deleteJob(jobId: Int): Resource<Int> =
        withContext(Dispatchers.IO) {
            tryCatch {
                val result = jobDao.deleteJob(jobId)
                Resource.Success(result)
            }
        }

    override suspend fun deleteAllJobs(): Resource<Int> =
        withContext(Dispatchers.IO) {
            tryCatch {
                val result = jobDao.deleteAllJobs()
                Resource.Success(result)
            }
        }

    override suspend fun doesJobExist(jobId: Int): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            tryCatch {
                val result = jobDao.doesJobExist(jobId)
                Resource.Success(result)
            }
        }

    override suspend fun upsertJobs(jobs: List<Job>): Resource<Unit> =
        withContext(Dispatchers.IO) {
            tryCatch {
                jobDao.upsertJobs(jobs)
                Resource.Success(Unit)
            }
        }

    override suspend fun getSavedJobs(): Resource<List<Job>> =
        withContext(Dispatchers.IO) {
            tryCatch {
                val result = jobDao.getSavedJobs()
                Resource.Success(result)
            }
        }

    override suspend fun updateSavedStatus(jobId: Int, saved: Boolean): Resource<Unit> =
        withContext(Dispatchers.IO) {
            tryCatch {
                jobDao.updateSavedStatus(jobId, saved)
                Resource.Success(Unit)
            }
        }
}


