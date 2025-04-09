package com.ahmedkhalifa.careerlinkapp.repo.user

import com.ahmedkhalifa.careerlinkapp.data.network.firebase.FirebaseService
import com.ahmedkhalifa.careerlinkapp.models.User
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import javax.inject.Inject

class UserRepoImp @Inject constructor(
    private val firebaseService: FirebaseService
)
: UserRepo {
    override suspend fun getUserInfo(): User? {
        return firebaseService.getUserInfo()
    }

    override suspend fun updateUserInfo(user: User): Resource<Unit> {
        return try {
            firebaseService.updateUserInfo(user)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

}