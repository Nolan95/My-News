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
    var metaId: Long = 0,
    @ColumnInfo(name="media_id")
    var mediaId: Long = 0,
    var format: String = "",
    var height: Int = 0,
    var url: String = "",
    var width: Int = 0
)