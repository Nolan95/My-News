package com.example.mynews.repository.roomdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.mynews.repository.data.Multimedia

@Entity(tableName = "docs")
data class DocEntity(
    @PrimaryKey var docId: Long = 0,
    var pub_date: String = "",
    var section_name: String = "",
    var snippet: String = "",
    var source: String = "",
    var subsection_name: String = "",
    var uri: String= "",
    var web_url: String = "",
    var word_count: Int = 0,
    @Ignore
    var multimedia: List<MultimediaEntity> = listOf()
)