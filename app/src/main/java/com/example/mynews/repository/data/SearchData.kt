package com.example.mynews.repository.data

import androidx.room.*

data class SearchData(
    val copyright: String = "",
    val status: String = "",
    val response: Response
)