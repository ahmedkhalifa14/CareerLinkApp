package com.ahmedkhalifa.careerlinkapp.repo.auth

import com.ahmedkhalifa.careerlinkapp.models.User
import com.ahmedkhalifa.careerlinkapp.utils.LoginResult

interface AuthRepo{
    suspend fun register(email: String, password: String)
    suspend fun login(email: String, password: String): LoginResult
    suspend fun saveUserData(user: User)
    suspend fun logout()
}