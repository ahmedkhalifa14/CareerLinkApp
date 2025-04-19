package com.ahmedkhalifa.careerlinkapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedkhalifa.careerlinkapp.helper.NetworkHelper
import com.ahmedkhalifa.careerlinkapp.models.ProfileUiState
import com.ahmedkhalifa.careerlinkapp.models.User
import com.ahmedkhalifa.careerlinkapp.repo.user.UserRepo
import com.ahmedkhalifa.careerlinkapp.utils.Event
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _userState = MutableStateFlow(ProfileUiState())
    val userState: StateFlow<ProfileUiState> = _userState.asStateFlow()

    private val _updateUserInfoState = MutableSharedFlow<Event<Resource<Unit>>>()
    val updateUserInfoState: SharedFlow<Event<Resource<Unit>>> = _updateUserInfoState.asSharedFlow()

    init {
        viewModelScope.launch {
            loadUserInfo()
        }
    }

    private suspend fun loadUserInfo() {
        _userState.update { it.copy(isLoading = true) }
        val user = userRepo.getUserInfo()
        _userState.update {
            it.copy(user = user ?: User(), isLoading = false)
        }


    }

    fun onChangeFirstName(value: String) {
        _userState.update {
            it.copy(user = it.user.copy(firstName = value))
        }
    }

    fun onChangeLastName(value: String) {
        _userState.update {
            it.copy(user = it.user.copy(lastName = value))
        }
    }

    fun onChangeLocation(value: String) {
        _userState.update {
            it.copy(user = it.user.copy(location = value))
        }
    }

    fun onChangeEmail(value: String) {
        _userState.update {
            it.copy(user = it.user.copy(email = value))
        }
    }

    fun onChangePhone(value: String) {
        _userState.update {
            it.copy(user = it.user.copy(phoneNumber = value))
        }
    }

    fun saveUserInformation() {
        viewModelScope.launch {
            if (!networkHelper.isConnected())
                _updateUserInfoState.emit(Event(Resource.Error("No internet connection")))
            _updateUserInfoState.emit(Event(Resource.Loading()))
            val result = userRepo.updateUserInfo(_userState.value.user)
            _updateUserInfoState.emit(Event(result))
        }
    }
}
