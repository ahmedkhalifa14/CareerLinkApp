package com.ahmedkhalifa.careerlinkapp.viewmodel

import android.util.Log
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedkhalifa.careerlinkapp.models.User
import com.ahmedkhalifa.careerlinkapp.repo.MainRepoImpl
import com.ahmedkhalifa.careerlinkapp.utils.Event
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainRepo: MainRepoImpl
) : ViewModel() {
    private val _registerStatus =
        MutableStateFlow<Event<Resource<Unit>>>(Event(Resource.Init()))
    val registerStatus: MutableStateFlow<Event<Resource<Unit>>> = _registerStatus
    private val _loginStatus = MutableStateFlow<Event<Resource<Unit>>>(Event(Resource.Init()))
    val loginStatus: MutableStateFlow<Event<Resource<Unit>>> = _loginStatus
    private val _saveUserDataStatus =
        MutableStateFlow<Event<Resource<Unit>>>(Event(Resource.Init()))
    val saveUserDataStatus: MutableStateFlow<Event<Resource<Unit>>> = _saveUserDataStatus

    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _registerStatus.value = Event(Resource.Loading())
            try {
                mainRepo.register(email, password)
                _registerStatus.value = Event(Resource.Success(Unit))
            } catch (e: Exception) {
                _registerStatus.value = Event(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }
    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginStatus.value = Event(Resource.Loading())
            try {
                mainRepo.login(email, password)
                _loginStatus.value = Event(Resource.Success(Unit))
            } catch (e: Exception) {
                _loginStatus.value = Event(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }
    fun saveUserData(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _saveUserDataStatus.value = Event(Resource.Loading())
            try {
                mainRepo.saveUserData(user)
                _saveUserDataStatus.value = Event(Resource.Success(Unit))
            } catch (e: Exception) {
                _saveUserDataStatus.value = Event(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

}