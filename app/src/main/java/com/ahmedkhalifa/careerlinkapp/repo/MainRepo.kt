package com.ahmedkhalifa.careerlinkapp.repo

import com.ahmedkhalifa.careerlinkapp.data.network.FirebaseService
import com.ahmedkhalifa.careerlinkapp.models.User
import javax.inject.Inject

interface MainRepo{
    suspend fun register(email: String, password: String)
    suspend fun login(email: String, password: String)
    suspend fun saveUserData(user: User)
}