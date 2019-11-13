package com.example.mynews.repository.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.repository.data.Result
import com.example.mynews.repository.roomdata.TopArticles
import io.reactivex.Single

@Dao
interface TopArticlesDao {

    @Query("SELECT * FROM toparticles WHERE type = :section_name")
    fun getTopStories(section_name: String): LiveData<List<TopArticles>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTopStories(articles: List<TopArticles>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopStories(article: TopArticles): Long

}