package com.example.mynews.repository.db


import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.repository.roomdata.SharedArticle
import com.example.mynews.repository.roomdata.SharedArticleAndMedia

@Dao
interface SharedArticleDao {

    @Query("SELECT * FROM sharedarticles")
    fun getSharedArticles(): DataSource.Factory<Int, SharedArticleAndMedia>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllArticles(sharedArticle: List<SharedArticle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(sharedArticle: SharedArticle): Long

}