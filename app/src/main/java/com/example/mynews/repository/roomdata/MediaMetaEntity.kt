package com.example.mynews.repository.roomdata

import androidx.room.*
import com.example.mynews.repository.data.MediaX

@Entity(tableName = "mediametas",
    indices = arrayOf(Index(value=["media_id"], unique = true)),
    foreignKeys = arrayOf(
    ForeignKey(
        entity = MediaEntity::class,
        parentColumns = arrayOf("mediaId"),
        childColumns = arrayOf("media_id")
    )
))

data class MediaMetaEntity(
    @PrimaryKey(autoGenerate = true)
    val metaId: Int = 0,
    @ColumnInfo(name="media_id")
    val mediaId: Int = 0,
    val format: String = "",
    val height: Int = 0,
    val url: String = "",
    val width: Int = 0
)