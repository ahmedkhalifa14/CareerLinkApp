package com.ahmedkhalifa.careerlinkapp.di

import android.content.Context
import com.ahmedkhalifa.careerlinkapp.data.local.datastore.DataStoreManager
import com.ahmedkhalifa.careerlinkapp.data.local.roomdb.JobDao
import com.ahmedkhalifa.careerlinkapp.data.network.api.JobsApiService
import com.ahmedkhalifa.careerlinkapp.data.network.firebase.FirebaseService
import com.ahmedkhalifa.careerlinkapp.helper.NetworkHelper
import com.ahmedkhalifa.careerlinkapp.repo.api.ApiRepo
import com.ahmedkhalifa.careerlinkapp.repo.api.ApiRepoImpl
import com.ahmedkhalifa.careerlinkapp.repo.auth.AuthRepo
import com.ahmedkhalifa.careerlinkapp.repo.auth.AuthRepoImpl
import com.ahmedkhalifa.careerlinkapp.repo.room.RoomDbRepo
import com.ahmedkhalifa.careerlinkapp.repo.room.RoomDbRepoImpl
import com.ahmedkhalifa.careerlinkapp.repo.settings.SettingsRepo
import com.ahmedkhalifa.careerlinkapp.repo.settings.SettingsRepoImpl
import com.ahmedkhalifa.careerlinkapp.repo.user.UserRepo
import com.ahmedkhalifa.careerlinkapp.repo.user.UserRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideAuthRepo(firebaseService: FirebaseService): AuthRepo =
        AuthRepoImpl(firebaseService)

    @Provides
    @Singleton
    fun provideApiRepo(apiService: JobsApiService,networkHelper: NetworkHelper,
                       roomDbRepo: RoomDbRepo,): ApiRepo =
        ApiRepoImpl(apiService,networkHelper,roomDbRepo)

    @Provides
    @Singleton
    fun provideSettingsRepo(dataStoreManager: DataStoreManager): SettingsRepo {
        return SettingsRepoImpl(dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideRoomDbRepo(jobDao: JobDao): RoomDbRepo {
        return RoomDbRepoImpl(jobDao)
    }

    @Provides
    @Singleton
    fun provideUserRepo(firebaseService: FirebaseService):UserRepo{
        return UserRepoImp(firebaseService)
    }

    @Singleton
    @Provides
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ) = context
}