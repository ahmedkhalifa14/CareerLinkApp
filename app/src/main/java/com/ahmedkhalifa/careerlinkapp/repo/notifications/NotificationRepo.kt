package com.ahmedkhalifa.careerlinkapp.repo.notifications

import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.utils.Resource

interface NotificationRepo {
    suspend fun triggerNotification(
        job: Job
    ):
            Resource<Boolean>
}