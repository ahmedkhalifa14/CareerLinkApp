package com.ahmedkhalifa.careerlinkapp.models

data class User(
    val userId: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val location: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val profilePictureLink: String = "",
    val joinedAt: Long = 0
)
