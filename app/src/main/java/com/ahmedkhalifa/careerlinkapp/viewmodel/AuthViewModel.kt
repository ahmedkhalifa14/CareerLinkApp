package com.ahmedkhalifa.careerlinkapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedkhalifa.careerlinkapp.models.User
import com.ahmedkhalifa.careerlinkapp.repo.auth.AuthRepo
import com.ahmedkhalifa.careerlinkapp.utils.Event
import com.ahmedkhalifa.careerlinkapp.utils.LoginResult
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val firebaseAuth: FirebaseAuth

) : ViewModel() {
    private val _registerState =
        MutableStateFlow<Event<Resource<Unit>>>(Event(Resource.Init()))
    val registerState: MutableStateFlow<Event<Resource<Unit>>> = _registerState
    private val _loginState =
        MutableStateFlow<Event<Resource<Unit>>>(Event(Resource.Init()))
    val loginState: MutableStateFlow<Event<Resource<Unit>>> = _loginState
    private val _saveUserDataState =
        MutableStateFlow<Event<Resource<Unit>>>(Event(Resource.Init()))
    val saveUserDataState: MutableStateFlow<Event<Resource<Unit>>> = _saveUserDataState

    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _registerState.value = Event(Resource.Loading())
            try {
                authRepo.register(email, password)
                _registerState.value = Event(Resource.Success(Unit))
            } catch (e: Exception) {
                _registerState.value = Event(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginState.value = Event(Resource.Loading())
            val result = authRepo.login(email, password)
            _loginState.value = when (result) {
                LoginResult.Success -> Event(Resource.Success(Unit))
                LoginResult.EmailNotVerified -> Event(Resource.Error("Email not verified"))
                LoginResult.EmailNotFound -> Event(Resource.Error("Email not found"))
                is LoginResult.Error -> Event(Resource.Error(result.message))
            }
        }
    }

    fun saveUserData(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _saveUserDataState.value = Event(Resource.Loading())
            try {
                authRepo.saveUserData(user)
                _saveUserDataState.value = Event(Resource.Success(Unit))
            } catch (e: Exception) {
                _saveUserDataState.value = Event(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

}