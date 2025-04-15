package com.ahmedkhalifa.careerlinkapp.data.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    suspend fun setBoolean(key: String, value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

//    fun getBoolean(key: String, defaultValue: Boolean = false): Flow<Boolean> {
//        return context.dataStore.data.map { preferences ->
//            preferences[booleanPreferencesKey(key)] ?: defaultValue
//        }

    fun getBoolean(key: String, defaultValue: Boolean = false): Flow<Boolean> {
        return context.dataStore.data
            .onEach {
                Log.d(
                    "DataStoreManager",
                    "Preferences Data: $it"
                )
            }
            .map { preferences ->
                val value = preferences[booleanPreferencesKey(key)] ?: defaultValue
                Log.d("DataStoreManager", "Key: $key | Emitted Value: $value")
                value
            }


    }
}