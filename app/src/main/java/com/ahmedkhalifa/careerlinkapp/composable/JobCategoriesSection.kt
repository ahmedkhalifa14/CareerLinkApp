package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ahmedkhalifa.careerlinkapp.models.Category

@Composable
fun JobCategoriesSection(
    categories: List<Category>?,
    isLoading: Boolean,
    errorMessage: String?,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        if (isLoading) {
            repeat(5) { ShimmerCircularCategoryCard() }
        } else if (errorMessage != null) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Error loading categories: $errorMessage")
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else if (categories != null) {

            categories.forEach { category ->
                CircularCategoryCard(category = category)
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else {

            Text("No Categories available.")
        }
    }
}