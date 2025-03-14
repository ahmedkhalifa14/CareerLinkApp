package com.ahmedkhalifa.careerlinkapp.utils

sealed class LoginResult {
    object Success : LoginResult()
    object EmailNotVerified : LoginResult()
    object EmailNotFound : LoginResult()
    data class Error(val message: String) : LoginResult()
}