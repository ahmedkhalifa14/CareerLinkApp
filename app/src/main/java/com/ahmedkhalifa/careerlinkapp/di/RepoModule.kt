package com.ahmedkhalifa.careerlinkapp.di

import android.content.Context
import com.ahmedkhalifa.careerlinkapp.data.local.DataStoreManager
import com.ahmedkhalifa.careerlinkapp.data.network.FirebaseService
import com.ahmedkhalifa.careerlinkapp.data.network.JobsApiService
import com.ahmedkhalifa.careerlinkapp.repo.ApiRepo
import com.ahmedkhalifa.careerlinkapp.repo.ApiRepoImpl
import com.ahmedkhalifa.careerlinkapp.repo.AuthRepo
import com.ahmedkhalifa.careerlinkapp.repo.AuthRepoImpl
import com.ahmedkhalifa.careerlinkapp.repo.SettingsRepo
import com.ahmedkhalifa.careerlinkapp.repo.SettingsRepoImpl
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
    fun provideApiRepo(apiService: JobsApiService): ApiRepo =
        ApiRepoImpl(apiService)

    @Provides
    @Singleton
    fun provideSettingsRepo(dataStoreManager: DataStoreManager): SettingsRepo {
        return SettingsRepoImpl(dataStoreManager)
    }
    @Singleton
    @Provides
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ) = context
}