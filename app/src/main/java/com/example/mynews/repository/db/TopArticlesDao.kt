package com.example.mynews.repository.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.mynews.repository.roomdata.TopArticlesAndMultimediaX
import com.example.mynews.repository.roomdata.TopArticles

@Dao
interface TopArticlesDao {
    @Transaction
    @Query("SELECT * FROM toparticles WHERE type = :section_name ORDER BY published_date DESC")
    fun getTopStories(section_name: String): LiveData<List<TopArticlesAndMultimediaX>>

    @Insert(onConflict = REPLACE)
    fun insertAllTopStories(articles: List<TopArticles>)

    @Insert(onConflict = REPLACE)
    fun insertTopStories(article: TopArticles): Long

}