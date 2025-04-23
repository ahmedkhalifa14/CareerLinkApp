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

    private val _updateAppliedJobState =
        MutableStateFlow<Event<Resource<Unit>>>(Event(Resource.Init()))
    val updateAppliedJobState: MutableStateFlow<Event<Resource<Unit>>> = _updateAppliedJobState


    private val _allJobsState =
        MutableStateFlow<Event<Resource<List<Job>>>>(Event(Resource.Init()))
    val allJobState: MutableStateFlow<Event<Resource<List<Job>>>> = _allJobsState
    private val _allAppliedJobsState =
        MutableStateFlow<Event<Resource<List<Job>>>>(Event(Resource.Init()))
    val allAppliedJobsState: MutableStateFlow<Event<Resource<List<Job>>>> = _allAppliedJobsState
    private val _deleteJobState =
        MutableStateFlow<Event<Resource<Int>>>(Event(Resource.Init()))
    val deleteJobState: MutableStateFlow<Event<Resource<Int>>> = _deleteJobState
    private val _deleteAllJobsState =
        MutableStateFlow<Event<Resource<Int>>>(Event(Resource.Init()))
    val deleteAllJobsState: MutableStateFlow<Event<Resource<Int>>> = _deleteAllJobsState

    private val _isJobSavedState =
            MutableStateFlow<Event<Resource<Job>>>(Event(Resource.Init()))
    val isJobSavedState: MutableStateFlow<Event<Resource<Job>>> = _isJobSavedState

    fun getSavedJobs() {
        viewModelScope.launch {
            _allJobsState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.getSavedJobs()
            _allJobsState.emit(Event(result))
        }
    }

    fun getAppliedJobs() {
        viewModelScope.launch {
            _allAppliedJobsState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.getAppliedJobs()
            _allAppliedJobsState.emit(Event(result))
        }
    }


    fun insertJob(job: Job) {
        viewModelScope.launch {
            _insertJobState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.insertJob(job)
            _insertJobState.emit(Event(result))
        }
    }

    fun deleteJob(jobId: Int) {
        viewModelScope.launch {
            _deleteJobState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.deleteJob(jobId)
            _deleteJobState.emit(Event(result))
        }
    }


    fun updateSavedStatus(jobId: Int, saved: Boolean) {
        viewModelScope.launch {
            _updateJobState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.updateSavedStatus(jobId, saved)
            _updateJobState.emit(Event(result))
        }
    }

    fun updateAppliedStatus(jobId: Int, applied: Boolean) {
        viewModelScope.launch {
            Log.d("ViewModel", "Starting updateAppliedStatus for jobId: $jobId, applied: $applied")
            _updateAppliedJobState.emit(Event(Resource.Loading()))
            val result = roomDbRepo.updateAppliedStatus(jobId, applied)
            Log.d("ViewModel", "Update result for jobId: $jobId: $result")
            _updateAppliedJobState.emit(Event(result))
        }
    }

}