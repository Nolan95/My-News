package com.example.mynews.repository.roomdata

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mynews.repository.data.Doc

class DocAndMultimedia{
    @Embedded val doc: Doc? = null

    @Relation(parentColumn = "docId", entityColumn = "multimediaId", entity = MultimediaEntity::class)
    var multimedia: List<MultimediaEntity> = listOf()
}