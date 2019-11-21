package com.example.mynews.repository.db

import androidx.paging.DataSource
import androidx.room.*
import com.example.mynews.repository.roomdata.TopArticlesAndMultimediaX
import com.example.mynews.repository.roomdata.TopArticles

@Dao
interface TopArticlesDao {
    @Transaction
    @Query("SELECT DISTINCT * FROM toparticles WHERE type = :section_name ORDER BY published_date DESC")
    fun getTopStories(section_name: String): DataSource.Factory<Int, TopArticlesAndMultimediaX>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTopStories(articles: List<TopArticles>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopStories(article: TopArticles): Long

}