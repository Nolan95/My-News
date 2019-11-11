package com.example.mynews.repository.roomdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.mynews.repository.data.MultimediaX

@Entity(tableName = "toparticles")
data class TopArticles(
    @PrimaryKey(autoGenerate = true)
    var topArticlesId: Int = 0,
    var published_date: String = "",
    var section: String = "",
    var short_url: String = "",
    var subsection: String = "",
    var title: String = "",
    var updated_date: String = "",
    var url: String = "",
    var id: Long = 0,
    var views: Int = 0,
    var source: String = ""
)