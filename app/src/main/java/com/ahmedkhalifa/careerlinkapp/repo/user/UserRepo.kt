package com.ahmedkhalifa.careerlinkapp.repo.user

import com.ahmedkhalifa.careerlinkapp.models.User
import com.ahmedkhalifa.careerlinkapp.utils.Resource

interface UserRepo {
    suspend fun getUserInfo():User?
    suspend fun updateUserInfo(user: User):Resource<Unit>
}