package com.example.mynews.repository.roomdata

import androidx.room.*
import com.example.mynews.repository.data.MediaMetadata
import com.example.mynews.repository.data.Result
import com.google.gson.annotations.SerializedName


@Entity(tableName = "medias")

data class MediaEntity(
    @PrimaryKey(autoGenerate = true)
    var mediaId: Long = 0,
    @ColumnInfo(name="shared_article_id")
    var sharedArticleId: Long = 0
)