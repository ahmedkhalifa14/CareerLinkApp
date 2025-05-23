package com.ahmedkhalifa.careerlinkapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahmedkhalifa.careerlinkapp.data.local.roomdb.JobRoomDatabase
import com.ahmedkhalifa.careerlinkapp.notification.AppNotificationManager
import com.ahmedkhalifa.careerlinkapp.helper.NetworkHelper
import com.ahmedkhalifa.careerlinkapp.repo.notifications.NotificationRepoImpl
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
            "job_database"
        )
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideJobDao(jobRoomDatabase: JobRoomDatabase) = jobRoomDatabase.jobDao()

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): AppNotificationManager {
        return AppNotificationManager(context)
    }

    @Provides
    @Singleton
    fun provideNotificationRepo(notificationManager: AppNotificationManager): com.ahmedkhalifa.careerlinkapp.repo.notifications.NotificationRepo {
        return NotificationRepoImpl(notificationManager)
    }
}