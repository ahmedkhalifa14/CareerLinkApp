package com.ahmedkhalifa.careerlinkapp.repo

import kotlinx.coroutines.flow.Flow

interface SettingsRepo {
    suspend fun setDarkMode(isDarkMode: Boolean)
    fun getDarkMode(): Flow<Boolean>
    suspend fun setUserLoggedIn(isLoggedIn: Boolean)
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun setFirstTimeLaunch(isFirstTimeLaunch: Boolean)
    fun isFirstTimeLaunch(): Flow<Boolean>

}