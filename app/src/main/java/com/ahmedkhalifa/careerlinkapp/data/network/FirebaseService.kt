package com.ahmedkhalifa.careerlinkapp.data.network

import android.util.Log
import com.ahmedkhalifa.careerlinkapp.models.User
import com.ahmedkhalifa.careerlinkapp.utils.LoginResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
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
    suspend fun saveUserInfo(user: User) {
        val documentReference = firebaseFirestore.collection("Users")
            .document(firebaseAuth.currentUser!!.uid)
        try {
            documentReference.set(user).await()
        } catch (e: Exception) {
            Log.d(
                "FirebaseService", "saveUserInfo: ${e.message}"
            )
        }
    }
}