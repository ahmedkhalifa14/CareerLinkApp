package com.ahmedkhalifa.careerlinkapp.repo

import com.ahmedkhalifa.careerlinkapp.data.network.FirebaseService
import com.ahmedkhalifa.careerlinkapp.models.User
import com.ahmedkhalifa.careerlinkapp.utils.LoginResult
import javax.inject.Inject

class MainRepoImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : MainRepo {
    override suspend fun register(email: String, password: String) {
        firebaseService.registerUser(email, password)
    }

    override suspend fun login(email: String, password: String): LoginResult {
        return firebaseService.loginUser(email, password)
    }



    override suspend fun saveUserData(user: User) {
       firebaseService.saveUserInfo(user)
    }

}