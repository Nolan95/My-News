package com.example.mynews.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.repository.data.Response
import com.example.mynews.repository.roomdata.SharedArticle

@Dao
interface SharedArticleDao {

    @Query("SELECT * FROM sharedarticles")
    fun getSharedArticles(): LiveData<List<SharedArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllArticles(sharedArticle: List<SharedArticle>)

}