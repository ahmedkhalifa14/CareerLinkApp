package com.ahmedkhalifa.careerlinkapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    data object Home:BottomBarScreen(
        route = "Home",
        title = "Home",
        icon = Icons.Default.Home
    )
    data object Profile : BottomBarScreen(
        route = "PROFILE",
        title = "Me",
        icon = Icons.Default.Person
    )
    data object Settings : BottomBarScreen(
        route = "SETTINGS",
        title = "Settings",
        icon = Icons.Default.Settings
    )

}