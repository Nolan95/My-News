package com.example.mynews.repository.roomdata

import androidx.room.Embedded
import androidx.room.Relation

class MediaAndMeta {
    @Embedded
    var media: MediaEntity? = null

    @Relation(parentColumn = "mediaId", entityColumn = "media_id", entity = MediaMetaEntity::class)
    var mediaMetadata: List<MediaMetaEntity> = listOf()
}