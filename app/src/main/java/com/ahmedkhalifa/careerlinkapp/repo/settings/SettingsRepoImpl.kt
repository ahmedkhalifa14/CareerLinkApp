package com.ahmedkhalifa.careerlinkapp.repo.settings

import android.util.Log
import com.ahmedkhalifa.careerlinkapp.data.local.datastore.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SettingsRepoImpl @Inject constructor(private val dataStoreManager: DataStoreManager) :
    SettingsRepo {

    private val DARK_MODE_KEY = "dark_mode"
    private val USER_LOGGED_IN_KEY = "user_logged_in"
    private val FIRST_TIME_LAUNCH_KEY = "first_time_launch"

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        dataStoreManager.setBoolean(DARK_MODE_KEY, isDarkMode)
    }

    override fun getDarkMode(): Flow<Boolean> {
        return dataStoreManager.getBoolean(DARK_MODE_KEY)
    }

    override suspend fun setUserLoggedIn(isLoggedIn: Boolean) {
        dataStoreManager.setBoolean(USER_LOGGED_IN_KEY, isLoggedIn)
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return dataStoreManager.getBoolean(USER_LOGGED_IN_KEY)
    }

    override suspend fun setFirstTimeLaunch(isFirstTimeLaunch: Boolean) {
        dataStoreManager.setBoolean(FIRST_TIME_LAUNCH_KEY, isFirstTimeLaunch)
        Log.d("repo", "setFirstTimeLaunch: $isFirstTimeLaunch")
    }

//    override fun isFirstTimeLaunch(): Flow<Boolean> {
//        return dataStoreManager.getBoolean(FIRST_TIME_LAUNCH_KEY, true)
//    }

    override  fun isFirstTimeLaunch(): Flow<Boolean> {
        Log.d("SettingsRepoImpl", "isFirstTimeLaunch() called") // Debug log
        return dataStoreManager.getBoolean(FIRST_TIME_LAUNCH_KEY, true)
            .onEach { Log.d("SettingsRepoImpl", "DataStore emits: $it") } // Debug log
    }

}