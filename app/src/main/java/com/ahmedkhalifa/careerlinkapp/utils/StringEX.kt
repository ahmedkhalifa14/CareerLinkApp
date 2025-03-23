package com.ahmedkhalifa.careerlinkapp.utils

fun String.truncate(maxLength: Int): String {
    if (this.length <= maxLength) {
        return this
    }
    return this.substring(0, maxLength) + "..."
}

fun String?.getDefault(default: String = "N/A"): String {
    return if (this.isNullOrBlank()) {
        default
    } else {
        this
    }
}