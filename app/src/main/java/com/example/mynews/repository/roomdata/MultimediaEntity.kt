package com.example.mynews.repository.roomdata

import androidx.room.*
import com.example.mynews.repository.data.Doc

@Entity(tableName = "multimedias",
    indices = arrayOf(Index(value=["doc_id"], unique = true)),
    foreignKeys = arrayOf(
    ForeignKey(
        entity = DocEntity::class,
        parentColumns = arrayOf("docId"),
        childColumns = arrayOf("doc_id")
    )
))
data class MultimediaEntity(
    @PrimaryKey(autoGenerate = true)
    var multimediaId: Int = 0,
    @ColumnInfo(name = "doc_id")
    var docId: Int = 0,
    var type: String = "",
    var url: String = ""
)