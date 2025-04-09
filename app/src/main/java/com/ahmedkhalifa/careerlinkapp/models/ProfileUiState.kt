package com.ahmedkhalifa.careerlinkapp.models


data class ProfileUiState(
    val user: User = User(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)