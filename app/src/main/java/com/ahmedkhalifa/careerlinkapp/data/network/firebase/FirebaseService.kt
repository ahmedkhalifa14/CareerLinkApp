package com.ahmedkhalifa.careerlinkapp.data.network.firebase

import android.util.Log
import com.ahmedkhalifa.careerlinkapp.models.User
import com.ahmedkhalifa.careerlinkapp.utils.LoginResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {
    suspend fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        firebaseAuth.currentUser?.sendEmailVerification()
    }

    suspend fun loginUser(email: String, password: String): LoginResult {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (firebaseAuth.currentUser?.isEmailVerified == true) {
                LoginResult.Success
            } else {
                LoginResult.EmailNotVerified
            }
        } catch (e: FirebaseAuthException) {
            when (e.errorCode) {
                "ERROR_USER_NOT_FOUND" -> LoginResult.EmailNotFound
                "ERROR_WRONG_PASSWORD" -> LoginResult.Error("Incorrect password") //Or other error handling.
                else -> LoginResult.Error(e.message ?: "An error occurred")
            }
        } catch (e: Exception) {
            LoginResult.Error(e.message ?: "An error occurred")
        }
    }
    suspend fun logoutUser() {
        firebaseAuth.signOut()
    }


    suspend fun saveUserInfo(user: User) {
        // Check if the user is logged in
        val currentUser = firebaseAuth.currentUser ?: return
        val documentReference = firebaseFirestore.collection("Users")
            .document(currentUser.uid)
        try {
            documentReference.set(user, SetOptions.merge()).await()
        } catch (e: Exception) {
            Log.d(
                "FirebaseService", "saveUserInfo: ${e.message}"
            )
        }
    }

    suspend fun getUserInfo(): User? {
        val currentUser = firebaseAuth.currentUser
            ?: return null
        val documentReference = firebaseFirestore.collection("Users")
            .document(currentUser.uid)
        return try {
            val documentSnapshot = documentReference.get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(User::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }


    suspend fun updateUserInfo(user: User) {
        val currentUser = firebaseAuth.currentUser ?: return
        val documentReference = firebaseFirestore.collection("Users")
            .document(currentUser.uid)
        Log.d("FirebaseService", "updateUserInfo: ${currentUser.uid}")
        try {
            documentReference.set(
                mapOf(
                    "firstName" to user.firstName,
                    "lastName" to user.lastName,
                    "location" to user.location,
                    "email" to user.email,
                    "phoneNumber" to user.phoneNumber
                ),
                SetOptions.merge()
            ).await()
        } catch (e: Exception) {
            Log.d("FirebaseService", "updateUserInfo: ${e.message}")
        }
    }



}