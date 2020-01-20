package com.example.mynews.repository.roomdata

import androidx.room.*
import com.example.mynews.repository.data.MultimediaX

@Entity(tableName = "toparticles", indices = [Index(value = ["articleId", "title", "url"], unique = true)])
data class TopArticles(
    @PrimaryKey(autoGenerate = true) var articleId: Long = 0,
    var published_date: String = "",
    var section: String = "",
    var short_url: String = "",
    var subsection: String = "",
    var title: String = "",
    var updated_date: String = "",
    var url: String = "",
    var views: Int = 0,
    var source: String = "",
    var type: String = ""
)