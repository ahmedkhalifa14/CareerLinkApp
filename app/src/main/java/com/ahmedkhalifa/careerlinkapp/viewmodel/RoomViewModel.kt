package com.ahmedkhalifa.careerlinkapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.repo.room.RoomDbRepo
import com.ahmedkhalifa.careerlinkapp.utils.Event
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val roomDbRepo: RoomDbRepo
) : ViewModel() {
    private val _insertJobState =
        MutableStateFlow<Event<Resource<Long>>>(Event(Resource.Init()))
    val insertJobState: MutableStateFlow<Event<Resource<Long>>> = _insertJobState

    private val _updateJobState =
        MutableStateFlow<Event<Resource<Unit>>>(Event(Resource.Init()))
    val updateJobState: MutableStateFlow<Event<Resource<Unit>>> = _updateJobState


    private val _allJobsState =
        MutableStateFlow<Event<Resource<List<Job>>>>(Event(Resource.Init()))
    val allJobState: MutableStateFlow<Event<Resource<List<Job>>>> = _allJobsState

    private val _deleteJobState =
        MutableStateFlow<Event<Resource<Int>>>(Event(Resource.Init()))
    val deleteJobState: MutableStateFlow<Event<Resource<Int>>> = _deleteJobState

    private val _deleteAllJobsState =
        MutableStateFlow<Event<Resource<Int>>>(Event(Resource.Init()))
    val deleteAllJobsState: MutableStateFlow<Event<Resource<Int>>> = _deleteAllJobsState

    private val _isJobSavedState =
        MutableStateFlow<Event<Resource<Boolean>>>(Event(Resource.Init()))
    val isJobSavedState: MutableStateFlow<Event<Resource<Boolean>>> = _isJobSavedState

    fun insertJob(job: Job) {
        viewModelScope.launch {
            _insertJobState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.insertJob(job)
            _insertJobState.emit(Event(result))
        }
    }

    fun getAllJobs() {
        viewModelScope.launch {
            _allJobsState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.getSavedJobs()
            _allJobsState.emit(Event(result))
        }
    }

    fun getSavedJobs() {
        viewModelScope.launch {
            _allJobsState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.getSavedJobs()
            Log.d("getSavedJobs", "getSavedJobs: ${result.data}")
            _allJobsState.emit(Event(result))
        }
    }

    fun deleteJob(jobId: Int) {
        viewModelScope.launch {
            _deleteJobState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.deleteJob(jobId)
            _deleteJobState.emit(Event(result))
        }
    }

    fun deleteAllJobs() {
        viewModelScope.launch {
            _deleteAllJobsState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.deleteAllJobs()
            _deleteAllJobsState.emit(Event(result))
        }
    }

    fun checkIfJobExists(jobId: Int) {
        viewModelScope.launch {
            _isJobSavedState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.doesJobExist(jobId)
            _isJobSavedState.emit(Event(result))
        }
    }

    fun updateSavedStatus(jobId: Int, saved: Boolean) {
        viewModelScope.launch {
            _updateJobState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.updateSavedStatus(jobId, saved)
            Log.d("TAG", "updateSavedStatus: $saved")
            _updateJobState.emit(Event(result))
            getSavedJobs()
            Log.d("TAG", "updateSavedStatus: $saved")
        }
    }


}