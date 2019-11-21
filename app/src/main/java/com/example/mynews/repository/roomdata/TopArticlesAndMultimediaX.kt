package com.example.mynews.repository.roomdata

import androidx.room.Embedded
import androidx.room.Relation

class TopArticlesAndMultimediaX {

    @Embedded
    var article: TopArticles? = null

    @Relation(parentColumn = "id", entityColumn = "top_articles_id", entity = MultimediaXEntity::class)
    var multimedia: List<MultimediaXEntity> = listOf()
}