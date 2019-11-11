package com.example.mynews.repository.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

data class MediaMetadata(
    val format: String = "",
    val height: Int = 0,
    val url: String = "",
    val width: Int = 0
)