package com.example.mynews.repository.roomdata

import androidx.room.Embedded
import androidx.room.Relation

class SharedArticleAndMedia {
    @Embedded
    var sharedArticle: SharedArticle? = null

    @Relation(parentColumn = "sharedArticleId", entityColumn = "shared_article_id", entity = MediaEntity::class)
    var medias: List<MediaAndMeta> = listOf()

}