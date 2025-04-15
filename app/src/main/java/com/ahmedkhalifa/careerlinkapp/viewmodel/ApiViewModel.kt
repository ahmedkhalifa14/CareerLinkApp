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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(private val mainRepo: ApiRepo) : ViewModel() {
    private val _remoteJobsState =
        MutableStateFlow<Event<Resource<ParentJob<Job>>>>(Event(Resource.Init()))
    val remoteJobsState: MutableStateFlow<Event<Resource<ParentJob<Job>>>> = _remoteJobsState
    private var currentLimit = 20

    private val _remoteJobsCategoriesState =
        MutableStateFlow<Event<Resource<ParentJob<Category>>>>(Event(Resource.Init()))
    val remoteJobsCategoriesState: MutableStateFlow<Event<Resource<ParentJob<Category>>>> =
        _remoteJobsCategoriesState
    fun getRemoteJobsCategories() {
        viewModelScope.launch {
            _remoteJobsCategoriesState.emit(Event(Resource.Loading()))
            val result = mainRepo.getRemoteJobsCategories()
            _remoteJobsCategoriesState.emit(Event(result))
        }
    }

    fun getRemoteJobs(limit: Int) {
        viewModelScope.launch {
            _remoteJobsState.emit(Event(Resource.Loading()))
            val result = mainRepo.getRemoteJobs(limit)
            _remoteJobsState.emit(Event(result))
        }
    }
    private var lastRequestTime: Long = 0
    private val requestDelay: Long = 10000 // 10 seconds delay

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




    private val _searchJobsState =
        MutableStateFlow<Event<Resource<ParentJob<Job>>>>(Event(Resource.Init()))
    val searchJobsState: MutableStateFlow<Event<Resource<ParentJob<Job>>>> = _searchJobsState

    fun searchForJobs(limit: Int, searchKeyword: String?) {
        viewModelScope.launch {
            _searchJobsState.emit(Event(Resource.Loading()))
            val result = mainRepo.searchForJobs(limit, searchKeyword)
            _searchJobsState.emit(Event(result))
        }

    }
}

