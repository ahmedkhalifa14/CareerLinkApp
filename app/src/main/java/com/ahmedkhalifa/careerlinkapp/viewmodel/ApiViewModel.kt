package com.ahmedkhalifa.careerlinkapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedkhalifa.careerlinkapp.models.Category
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.models.ParentJob
import com.ahmedkhalifa.careerlinkapp.repo.api.ApiRepo
import com.ahmedkhalifa.careerlinkapp.utils.Event
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(private val mainRepo: ApiRepo) : ViewModel() {

    private val _remoteJobsState =
        MutableStateFlow<Event<Resource<ParentJob<Job>>>>(Event(Resource.Init()))
    val remoteJobsState: StateFlow<Event<Resource<ParentJob<Job>>>> = _remoteJobsState

    private val _remoteJobsCategoriesState =
        MutableStateFlow<Event<Resource<ParentJob<Category>>>>(Event(Resource.Init()))
    val remoteJobsCategoriesState: StateFlow<Event<Resource<ParentJob<Category>>>> = _remoteJobsCategoriesState

    private val _savedJobsState =
        MutableStateFlow<Resource<List<Job>>>(Resource.Loading())
    val savedJobsState: StateFlow<Resource<List<Job>>> = _savedJobsState

    private val _searchJobsState =
        MutableStateFlow<Event<Resource<ParentJob<Job>>>>(Event(Resource.Init()))
    val searchJobsState: StateFlow<Event<Resource<ParentJob<Job>>>> = _searchJobsState

    private var currentLimit = 20
    private var lastRequestTime: Long = 0
    private val requestDelay: Long = 10000 // 10 seconds

    fun getRemoteJobs(limit: Int = 20) {
        viewModelScope.launch {
            _remoteJobsState.emit(Event(Resource.Loading()))
            val result = mainRepo.getRemoteJobs(limit)
            _remoteJobsState.emit(Event(result))
        }
    }

    fun loadMoreJobs() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastRequestTime >= requestDelay) {
            currentLimit += 5
            getRemoteJobs(currentLimit)
            lastRequestTime = currentTime
        } else {
            Log.d("ApiViewModel", "Request blocked due to rate limiting.")
        }
    }

    fun getRemoteJobsCategories() {
        viewModelScope.launch {
            _remoteJobsCategoriesState.emit(Event(Resource.Loading()))
            val result = mainRepo.getRemoteJobsCategories()
            _remoteJobsCategoriesState.emit(Event(result))
        }
    }

    fun searchForJobs(limit: Int, searchKeyword: String?) {
        viewModelScope.launch {
            _searchJobsState.emit(Event(Resource.Loading()))
            val result = mainRepo.searchForJobs(limit, searchKeyword)
            _searchJobsState.emit(Event(result))
        }
    }

    fun toggleSavedStatus(jobId: Int, saved: Boolean) {
        viewModelScope.launch {
            // Optimistically update saved status in the current list (without re-fetching)
            val updatedJobs = _remoteJobsState.value.peekContent().data?.jobs?.map { job ->
                if (job.id == jobId) {
                    job.copy(saved = saved)  // Update the specific job
                } else {
                    job
                }
            } ?: emptyList()

            // Emit the updated list
            _remoteJobsState.emit(
                Event(
                    Resource.Success(
                        ParentJob(
                            legal_notice = "",
                            warning = "",
                            job_count = updatedJobs.size,
                            jobs = updatedJobs
                        )
                    )
                )
            )

            // Save the updated job in the local database
            mainRepo.toggleSavedStatus(jobId, saved)
        }
    }


    fun loadSavedJobs() {
        viewModelScope.launch {
            _savedJobsState.value = Resource.Loading()
            val result = mainRepo.getSavedJobs()
            _savedJobsState.emit(result)
        }
    }
}
