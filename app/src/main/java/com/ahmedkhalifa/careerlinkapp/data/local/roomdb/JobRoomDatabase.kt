package com.ahmedkhalifa.careerlinkapp.data.local.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.utils.Converters


@Database(entities = [Job::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class JobRoomDatabase:RoomDatabase() {
    abstract fun jobDao(): JobDao
}