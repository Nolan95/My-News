package com.example.mynews.repository.roomdata

import androidx.room.*
import com.example.mynews.repository.data.Doc

@Entity(tableName = "multimediax", indices = arrayOf(Index(value = ["multimediaxId"], unique = true)))
data class MultimediaXEntity(
    @PrimaryKey(autoGenerate = true)
    var multimediaxId: Int = 0,
    @ColumnInfo(name = "top_articles_id")
    var topArticlesId: Long = 0,
    var type: String = "",
    var url: String = ""
)