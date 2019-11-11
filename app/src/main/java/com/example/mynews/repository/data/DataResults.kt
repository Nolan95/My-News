package com.example.mynews.repository.data

import androidx.room.*

data class DataResults(
    val copyright: String,
    val last_updated: String = "",
    val results: List<Result>,
    val num_results: Int,
    val section: String = "",
    val status: String
)