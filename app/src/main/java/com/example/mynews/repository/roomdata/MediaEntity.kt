package com.example.mynews.repository.roomdata

import androidx.room.*
import com.example.mynews.repository.data.MediaMetadata
import com.example.mynews.repository.data.Result
import com.google.gson.annotations.SerializedName


@Entity(tableName = "medias",
    indices = arrayOf(Index(value=["shared_article_id"], unique = true)),
    foreignKeys = arrayOf(
    ForeignKey(
        entity = SharedArticle::class,
        parentColumns = arrayOf("sharedArticleId"),
        childColumns = arrayOf("shared_article_id")
    )
))

data class MediaEntity(
    @PrimaryKey(autoGenerate = true)
    var mediaId: Int = 0,
    @Ignore
    @SerializedName("media-metadata") var mediaMetadata: List<MediaMetadata> = listOf(),
    @ColumnInfo(name="shared_article_id")
    var sharedArticleId: Int = 0
)