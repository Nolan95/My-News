package com.example.mynews.repository.roomdata

import androidx.room.*
import com.example.mynews.repository.data.Doc

@Entity(tableName = "multimediax",
    indices = arrayOf(Index(value=["top_articles_id"], unique = true)),
    foreignKeys = arrayOf(
    ForeignKey(
        entity = TopArticles::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("top_articles_id")
    )
))
data class MultimediaXEntity(
    @PrimaryKey(autoGenerate = true)
    var multimediaxId: Int = 0,
    @ColumnInfo(name = "top_articles_id")
    var topArticlesId: Long = 0,
    var type: String = "",
    var url: String = ""
)