package com.ahmedkhalifa.careerlinkapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.repo.notifications.NotificationRepo
import com.ahmedkhalifa.careerlinkapp.utils.Event
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepo: NotificationRepo
) : ViewModel() {
    private val _sendNotificationState =
        MutableStateFlow<Event<Resource<Boolean>>>(Event(Resource.Loading()))
    val sendNotificationState: StateFlow<Event<Resource<Boolean>>> = _sendNotificationState
    fun sendNotification(
        job: Job
    ) {
        viewModelScope.launch {
            _sendNotificationState.emit(Event(Resource.Loading()))
            delay(300)
            val result = notificationRepo.triggerNotification(
                job
            )
            _sendNotificationState.emit(Event(result))
        }

    }

}