package com.ahmedkhalifa.careerlinkapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahmedkhalifa.careerlinkapp.data.local.roomdb.JobRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJobRoomDatabase(@ApplicationContext context: Context): JobRoomDatabase {
        return Room.databaseBuilder(
            context,
            JobRoomDatabase::class.java,
            "job_database")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideJobDao(jobRoomDatabase: JobRoomDatabase) = jobRoomDatabase.jobDao()
}