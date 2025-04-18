package com.ahmedkhalifa.careerlinkapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories_table")
data class Category(
    @PrimaryKey
    val id: Int,
    val name: String,
    val slug: String
)