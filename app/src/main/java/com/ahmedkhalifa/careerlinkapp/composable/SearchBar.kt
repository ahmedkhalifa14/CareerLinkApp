package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchBar() {
    OutlinedTextField(value = "", onValueChange = {}, leadingIcon = {
        Icon(Icons.Filled.Search, contentDescription = "Search")
    }, placeholder = { Text(text = "Search Here...") },
        modifier = Modifier.fillMaxWidth()
    )
}