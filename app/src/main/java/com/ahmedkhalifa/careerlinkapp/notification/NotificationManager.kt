package com.ahmedkhalifa.careerlinkapp.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import coil.imageLoader
import coil.request.ImageRequest
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.models.Job
import kotlinx.coroutines.runBlocking
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import android.app.NotificationManager as SystemNotificationManager

class AppNotificationManager(private val context: Context) {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "CareerLink"
        const val NOTIFICATION_CHANNEL_NAME = "CareerLink Notifications"
        private var notificationIdCounter = 0
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as SystemNotificationManager
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            SystemNotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.BLUE
            enableVibration(true)
            description = "Get important CareerLink updates"
        }
        notificationManager.createNotificationChannel(channel)
    }

    fun sendApplicationNotification(job: Job): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }

            val largeIcon: Bitmap? = job.company_logo_url?.let { url ->
                runBlocking {
                    val request = ImageRequest.Builder(context)
                        .data(url)
                        .allowHardware(false)
                        .build()
                    val result = context.imageLoader.execute(request).drawable
                    (result as? BitmapDrawable)?.bitmap
                }
            }

            val jobTitle = job.title ?: ""
            val encodedJobTitle = URLEncoder.encode(jobTitle, StandardCharsets.UTF_8.toString())
            val deepLinkUri =
                "careerlink://application_status?jobId=${job.id}&jobTitle=$encodedJobTitle".toUri()

            val intent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
                setPackage(context.packageName)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notificationTitle = context.getString(R.string.notification_title, job.title ?: "")
            val notificationBody =
                context.getString(R.string.notification_body, job.company_name ?: "")

            val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setSmallIcon(R.drawable.app_logo)
                .setLargeIcon(largeIcon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as SystemNotificationManager
            notificationManager.notify(notificationIdCounter++, notification)
            true
        } catch (e: Exception) {
            Log.e("AppNotificationManager", "Failed to send notification: ${e.message}")
            e.printStackTrace()
            false
        }
    }

}
