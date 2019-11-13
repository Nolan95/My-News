package com.example.mynews.repository.roomdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.mynews.repository.data.MediaX
import com.example.mynews.repository.data.MultimediaX

@Entity(tableName = "sharedarticles")
data class SharedArticle(
    @PrimaryKey(autoGenerate = true)
    var sharedArticleId: Long = 0,
    var published_date: String = "",
    var section: String = "",
    var title: String = "",
    var updated_date: String = "",
    var url: String = "",
    var id: Long = 0,
    var views: Int = 0,
    var source: String = "",
    @Ignore var medias: List<MediaEntity> = listOf()

)