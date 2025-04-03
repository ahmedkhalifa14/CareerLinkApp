package com.ahmedkhalifa.careerlinkapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun timeSince(timestamp: String): String {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val dateTime = LocalDateTime.parse(timestamp, formatter)
    val now = LocalDateTime.now(ZoneId.of("UTC"))
    val duration = Duration.between(dateTime, now)

    return when {
        duration.seconds < 60 -> "${duration.seconds} seconds ago"
        duration.toMinutes() < 60 -> "${duration.toMinutes()} minutes ago"
        duration.toHours() < 24 -> "${duration.toHours()} hours ago"
        duration.toDays() < 30 -> "${duration.toDays()} days ago"
        duration.toDays() < 365 -> "${duration.toDays() / 30} months ago"
        else -> "${duration.toDays() / 365} years ago"
    }
}