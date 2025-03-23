package com.ahmedkhalifa.careerlinkapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedkhalifa.careerlinkapp.models.ParentJob
import com.ahmedkhalifa.careerlinkapp.repo.ApiRepo
import com.ahmedkhalifa.careerlinkapp.utils.Event
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(private val mainRepo: ApiRepo) : ViewModel() {
    private val _remoteJobsState =
        MutableStateFlow<Event<Resource<ParentJob>>>(Event(Resource.Init()))
    val remoteJobsState: MutableStateFlow<Event<Resource<ParentJob>>> = _remoteJobsState
    private var currentLimit = 20

    fun getRemoteJobs(limit: Int) {
        viewModelScope.launch {
            _remoteJobsState.emit(Event(Resource.Loading()))
            val result = mainRepo.getRemoteJobs(limit)
            _remoteJobsState.emit(Event(result))
        }
    }
    fun loadMoreJobs() {
        currentLimit += 5
        Log.d("Viewmodel Log", "current limit is $currentLimit")
        getRemoteJobs(currentLimit)
    }

    private val _searchJobsState =
        MutableStateFlow<Event<Resource<ParentJob>>>(Event(Resource.Init()))
    val searchJobsState: MutableStateFlow<Event<Resource<ParentJob>>> = _searchJobsState

    fun searchForJobs(page: Int, searchKeyword: String?) {
        viewModelScope.launch {
            _searchJobsState.emit(Event(Resource.Loading()))
            val result = mainRepo.searchForJobs(page, searchKeyword)
            _searchJobsState.emit(Event(result))
        }

    }
}

