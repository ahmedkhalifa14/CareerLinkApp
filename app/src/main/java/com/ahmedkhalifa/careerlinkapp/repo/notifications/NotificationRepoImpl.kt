package com.ahmedkhalifa.careerlinkapp.repo.notifications

import com.ahmedkhalifa.careerlinkapp.notification.AppNotificationManager
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.ahmedkhalifa.careerlinkapp.utils.Utils.tryCatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationRepoImpl @Inject constructor
    (private val notificationManager: AppNotificationManager) :
    NotificationRepo {
    override suspend fun triggerNotification(job: Job): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            tryCatch {
                val notificationResult = notificationManager.sendApplicationNotification(job)
                Resource.Success(notificationResult)
            }
        }

}

