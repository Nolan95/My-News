package com.example.mynews.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.repository.data.MultimediaX
import com.example.mynews.repository.roomdata.MultimediaXEntity

@Dao
interface MultimediaXDao {

    @Query("SELECT * FROM multimediax WHERE top_articles_id = :topArticlesId")
    fun getMultimediax(topArticlesId: Int): List<MultimediaXEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMultimediax(multimediax: List<MultimediaXEntity>?)
}